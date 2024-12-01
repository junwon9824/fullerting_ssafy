package com.ssafy.fullerting.deal.controller;

import com.ssafy.fullerting.alarm.service.EventAlarmService;
import com.ssafy.fullerting.bidLog.model.dto.request.BidProposeRequest;
import com.ssafy.fullerting.bidLog.model.entity.BidLog;
import com.ssafy.fullerting.bidLog.service.BidService;
import com.ssafy.fullerting.deal.model.dto.request.DealstartRequest;
import com.ssafy.fullerting.deal.model.dto.response.DealstartResponse;
import com.ssafy.fullerting.exArticle.exception.ExArticleErrorCode;
import com.ssafy.fullerting.exArticle.exception.ExArticleException;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.repository.ExArticleRepository;
import com.ssafy.fullerting.global.config.BidProducerService;
import com.ssafy.fullerting.security.model.entity.CustomAuthenticationToken;
import com.ssafy.fullerting.user.model.entity.MemberProfile;
import com.ssafy.fullerting.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ExArticleRepository exArticleRepository;
    private final EventAlarmService eventAlarmService;
    private final BidService bidService;
    private final UserService userService;
    private final BidProducerService bidProducerService;

    //이 메서드를 호출하는건 pub 로 시작하는 경로..
    @MessageMapping("/bidding/{exArticleId}/messages")
    public void bidBroker(@DestinationVariable Long exArticleId, SimpMessageHeaderAccessor headerAccessor, DealstartRequest dealstartRequest) {
        // 세션 속성에서 CustomAuthenticationToken 조회
        CustomAuthenticationToken authentication = (CustomAuthenticationToken) headerAccessor.getSessionAttributes().get("userAuthentication");

        if (authentication != null) {
            Long bidUserId = authentication.getUserId();
            MemberProfile bidUser = userService.getUserEntityById(bidUserId);

            log.info("웹소켓에서 추출한 유저 : {}", bidUser.toString());

            // 게시물 정보
            ExArticle exArticle = exArticleRepository.findById(exArticleId).orElseThrow(() -> new ExArticleException(
                    ExArticleErrorCode.NOT_EXISTS));

            // 최고가 검증
            int maxBidPrice = bidService.getMaxBidPrice(exArticle);
            if (dealstartRequest.getDealCurPrice() <= maxBidPrice || dealstartRequest.getDealCurPrice() < exArticle.getDeal().getDealCurPrice()) {
                throw new RuntimeException("최고가보다 낮거나 같은 입찰가 입력 : " + maxBidPrice);
            }


            // 입찰 기록(bid_log) 저장
            BidLog socketdealbid = bidService.socketdealbid(exArticle,
                    BidProposeRequest.builder()
                            .dealCurPrice(dealstartRequest.getDealCurPrice())
                            .userId(bidUserId)
                            .build());

            // 현재 입찰 참여자 수
            int bidderCount = bidService.getBidderCount(exArticle);
            log.info("bidCount {}", bidderCount);

            // WebSocket을 통해 메시지 전송
            messagingTemplate.convertAndSend("/sub/bidding/" + exArticleId,
                    DealstartResponse.builder()
                            .bidLogId(socketdealbid.getId())
                            .exArticleId(bidUserId)
                            .userResponse(bidUser.toResponse())
                            .dealCurPrice(dealstartRequest.getDealCurPrice())
                            .maxPrice(maxBidPrice)
                            .bidderCount(bidderCount)
                            .build());

            // Kafka로 메시지 전송
            DealstartResponse kafkaMessage = DealstartResponse.builder()
                    .bidLogId(socketdealbid.getId())
                    .exArticleId(bidUserId)
                    .userResponse(bidUser.toResponse())
                    .dealCurPrice(dealstartRequest.getDealCurPrice())
                    .maxPrice(maxBidPrice)
                    .bidderCount(bidderCount)
                    .build();

//            bidProducerService.sendBidMessage(exArticleId, kafkaMessage); // Kafka 토픽으로 메시지 전송

            log.info("Message [{}] sent by member: {} to bidding room: {}", dealstartRequest.getDealCurPrice(), exArticleId);
            log.info("리디렉트 URL: {}", dealstartRequest.getRedirectURL());

            // 입찰 알림 -->  이 부분을 이제 카프카를 사용하여 변경할거야
            //eventAlarmService.notifyAuctionBidReceived(bidUser, exArticle, dealstartRequest.getRedirectURL());


            // 카프카를 사용하여 입찰 알림 전송
//            bidProducerService.sendBidNotificationMessage(bidUser, exArticle,dealstartRequest.getRedirectURL()); // 수정된 부분
            bidProducerService.test(bidUser, exArticle,dealstartRequest.getRedirectURL()); // 수정된 부분


        } else {
            log.error("웹소켓 요청에 유저 정보없음");
        }
    }


}
