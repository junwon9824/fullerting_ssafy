package com.ssafy.fullerting.global.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.fullerting.alarm.service.EventAlarmService;
import com.ssafy.fullerting.bidLog.model.dto.request.BidProposeRequest;
//import com.ssafy.fullerting.bidLog.model.entity.BidLog;
import com.ssafy.fullerting.bidLog.model.entity.BidLog;
import com.ssafy.fullerting.bidLog.service.BidService;
import com.ssafy.fullerting.deal.exception.DealErrorCode;
import com.ssafy.fullerting.deal.exception.DealException;
import com.ssafy.fullerting.deal.model.dto.request.DealstartRequest;
import com.ssafy.fullerting.deal.model.dto.response.DealstartResponse;
import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.deal.repository.DealRepository;
import com.ssafy.fullerting.exArticle.exception.ExArticleErrorCode;
import com.ssafy.fullerting.exArticle.exception.ExArticleException;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.repository.ExArticleRepository;
import com.ssafy.fullerting.exArticle.service.ExArticleService;
import com.ssafy.fullerting.global.config.BidNotification;
import com.ssafy.fullerting.user.model.entity.MemberProfile;
import com.ssafy.fullerting.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j

public class BidConsumerService {

    private final EventAlarmService eventAlarmService;
    private final UserService userService;
    private final ExArticleService exArticleService;
    private final BidService bidService;
    private final ExArticleRepository exArticleRepository;
    private final DealRepository dealRepository;
    private final ObjectMapper objectMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "kafka-alarm", groupId = "user-notifications", containerFactory = "kafkaJsonContainerFactory")
    public void kafkaalram(String string) {
        long receivedTime = System.currentTimeMillis();
        log.info("Received notification: {} at {}", string, receivedTime);
        
        // 수신 시간과 전송 시간을 비교하여 속도 측정
        try {
            // 수신한 BidNotification 객체를 처리하는 로직
            System.out.println("Received notification for member: " + string);
            BidNotification bidNotification = objectMapper.readValue(string, BidNotification.class);

            MemberProfile buyer = userService.getUserEntityById(bidNotification.getUserId());
            ExArticle article = exArticleService.getbyid(bidNotification.getArticleId());
            Deal deal=dealRepository.findById(article.getDeal().getId() ).orElseThrow(()->new DealException(DealErrorCode.NOT_EXISTS));

            // 게시물 정보
            ExArticle exArticle = exArticleRepository.findById(bidNotification.getArticleId()).orElseThrow(() -> new ExArticleException(
                    ExArticleErrorCode.NOT_EXISTS));
            Long bidUserId= bidNotification.getUserId();

            MemberProfile bidUser = userService.getUserEntityById(bidUserId);
            DealstartRequest dealstartRequest = DealstartRequest.builder()
                    .dealCurPrice(bidNotification.getPrice())
                    .redirectURL(bidNotification.getRedirectUrl())
                    .exArticleId(bidNotification.getArticleId())
                    .build();


            // 입찰 기록(bid_log) 저장
            BidLog socketdealbid = bidService.socketdealbid(exArticle,
                    BidProposeRequest.builder()
                            .dealCurPrice(dealstartRequest.getDealCurPrice())
                            .userId(bidNotification.getUserId())
                            .build());

            int bidderCount = bidService.getBidderCount(deal);

            int maxBidPrice = bidService.getMaxBidPrice(exArticle);
            if (dealstartRequest.getDealCurPrice() <= maxBidPrice || dealstartRequest.getDealCurPrice() < exArticle.getDeal().getDealCurPrice()) {
                throw new RuntimeException("최고가보다 낮거나 같은 입찰가 입력 : " + maxBidPrice);
            }
            // WebSocket을 통해 메시지 전송
            messagingTemplate.convertAndSend("/sub/bidding/" + bidNotification.getArticleId(),
                    DealstartResponse.builder()
                            .bidLogId(Long.valueOf(socketdealbid.getId()))
                            .exArticleId(bidNotification.getUserId())
                            .userResponse(bidUser.toResponse())
                            .dealCurPrice(dealstartRequest.getDealCurPrice())
                            .maxPrice(maxBidPrice)
                            .bidderCount(bidderCount)
                            .build());

            eventAlarmService.notifyChatRoomAuthor(buyer, article, bidNotification.getRedirectUrl());


            // 추가적인 처리 로직을 여기에 구현
        } catch (Exception e) {
            // 예외 타입에 따라 다른 처리를 할 수 있습니다.
            System.err.println("Error processing bid notification: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            e.printStackTrace();

        }

    }

}
