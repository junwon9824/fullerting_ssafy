package com.ssafy.fullerting.deal.controller;

import com.ssafy.fullerting.alarm.service.EventAlarmService;
import com.ssafy.fullerting.bidLog.model.dto.request.BidProposeRequest;
import com.ssafy.fullerting.bidLog.model.entity.BidLog;
import com.ssafy.fullerting.bidLog.repository.BidRepository;
import com.ssafy.fullerting.bidLog.service.BidService;
import com.ssafy.fullerting.deal.model.dto.request.DealstartRequest;
import com.ssafy.fullerting.deal.model.dto.response.DealstartResponse;
import com.ssafy.fullerting.exArticle.exception.ExArticleErrorCode;
import com.ssafy.fullerting.exArticle.exception.ExArticleException;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.repository.ExArticleRepository;
import com.ssafy.fullerting.security.model.entity.CustomAuthenticationToken;
import com.ssafy.fullerting.user.exception.UserErrorCode;
import com.ssafy.fullerting.user.exception.UserException;
import com.ssafy.fullerting.user.model.dto.response.UserResponse;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import com.ssafy.fullerting.user.repository.UserRepository;
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
public class MessageController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ExArticleRepository exArticleRepository;
    private final EventAlarmService eventAlarmService;
    private final BidService bidService;
    private final UserService userService;

    @MessageMapping("/bidding/{exArticleId}/messages")
    public void bidBroker(@DestinationVariable Long exArticleId, SimpMessageHeaderAccessor headerAccessor, DealstartRequest dealstartRequest) {
        // 클라이언트가 보내야하는 정보(send)
        // 입찰자ID(세션에 포함), 거래글ID, 입찰금액, 본인페이지 URL
        // 클라이언트가 받아야하는 정보
        // 입찰로그ID, 거래글ID, 유저객체, 입찰자의 금액, 최고가격, 현재 입찰 참여자 수,

        // 세션 속성에서 CustomAuthenticationToken 조회
        CustomAuthenticationToken authentication = (CustomAuthenticationToken) headerAccessor.getSessionAttributes().get("userAuthentication");

        if (authentication != null) {
            // 여기에서 authentication.getUserId() 등을 통해 사용자 ID를 사용할 수 있음
            Long bidUserId = authentication.getUserId();
            UserResponse bidUser = userService.getUserInfobyid(bidUserId);
            log.info("웹소켓에서 추출한 유저 : {}", bidUser.toString());

            // 게시물 정보
            ExArticle exArticle = exArticleRepository.findById(exArticleId).orElseThrow(() -> new ExArticleException(
                    ExArticleErrorCode.NOT_EXISTS));

            // 최고가 검증
            // bid 서비스에서 맥스값 뽑는 메서드
            int maxBidPrice = bidService.getMaxBidPrice(exArticle);
            log.info("현재 최고가: {} ", maxBidPrice);
            log.info("현재가 : {}", exArticle.getDeal().getDealCurPrice());
            if (dealstartRequest.getDealCurPrice() <= maxBidPrice || dealstartRequest.getDealCurPrice() < exArticle.getDeal().getDealCurPrice()) {
                throw new RuntimeException("최고가보다 낮거나 같은 입찰가 입력 : " + maxBidPrice);
            }

            // 입찰 기록(bid_log) 저장
            BidLog socketdealbid = bidService.socketdealbid(exArticle,
                    BidProposeRequest.builder()
                            .dealCurPrice(dealstartRequest.getDealCurPrice())
                            .userId(bidUserId)
                            .build());

            // bid_log에서 distinct로 유일한 user_id 갯수 추출한 값 현재 입찰 참여자 수로 보내기
            int bidderCount = bidService.getBidderCount(exArticle);

            // 구독할 때 -> 서버에서 어떤 이벤트나 데이터의 변경이 생기는 경우
            // 서버가 해당 sub를 구독하고 있는 유저들에게 메시지 전달
            messagingTemplate.convertAndSend("/sub/bidding/" + exArticleId,
                    DealstartResponse.builder()
                            .bidLogId(socketdealbid.getId())
                            .exArticleId(bidUserId)
                            .userResponse(bidUser)
                            .dealCurPrice(dealstartRequest.getDealCurPrice())
                            .maxPrice(maxBidPrice)
                            .bidderCount(bidderCount)
                            .build()
            );

            log.info("Message [{}] send by member: {} to chatting room: {}", dealstartRequest.getDealCurPrice(), exArticleId);

            // 입찰 알림
//            CustomUser bidUser = userRepository.findById(bidUserId).orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER));
//            eventAlarmService.notifyAuctionBidReceived(bidUser, exArticle, redirectURL);

        } else {
            log.error("웹소켓 요청에 유저 정보없음");
        }
    }

//    @SubscribeMapping("/sub/chattings/{chattingRoomId}")
//    public void subscribeToChatRoom(@DestinationVariable Long chattingRoomId) {
//        // 해당 채널을 구독하는 메서드 내용 추가
//        log.info("User subscribed to chatting room: {}", chattingRoomId);
//    }

}
