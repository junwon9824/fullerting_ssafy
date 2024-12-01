package com.ssafy.fullerting.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.fullerting.deal.model.dto.response.DealstartResponse;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.user.model.entity.MemberProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.*;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class BidProducerService {
//    private final KafkaTemplate<String, BidNotification> kafkaTemplate; // Object 타입으로 변경하여 다양한 메시지 타입 지원
        private final KafkaTemplate<String, String> kafkaTemplatetest; // Object 타입으로 변경하여 다양한 메시지 타입 지원
    private final ObjectMapper objectMapper; // Jackson ObjectMapper


    // DealstartResponse 메시지 전송
//    public void sendBidMessage(Long exArticleId, DealstartResponse dealstartResponse) {
//        String topicName = "bidding-" + exArticleId; // 채팅방 ID에 따라 동적으로 토픽 이름 생성
//        kafkaTemplate.send(topicName, String.valueOf(dealstartResponse.getBidLogId()), dealstartResponse);
//    }


//    public void sendBidNotificationMessage(MemberProfile memberProfile, ExArticle exArticle, String redirecturl) {
//        String topicName = "bidding-notifications"; // 알림 전용 토픽
//
//        BidNotification bidNotification = BidNotification.builder()
//                .userId(memberProfile.getId())
//                .articleId(exArticle.getId())
//                .redirectUrl(redirecturl)
//                .build();
//
//        CompletableFuture<SendResult<String, BidNotification>> future = kafkaTemplate.send(topicName, bidNotification);
//
//        future
//                .thenAccept(result -> {
//                    log.info("Message sent to topic {} with offset {}", topicName, result.getRecordMetadata().offset());
//                })
//                .exceptionally(ex -> {
//                    log.error("Failed to send message to topic {} due to {}", topicName, ex.getMessage());
//                    return null; // Exceptionally는 Void를 반환하므로 null 반환
//                });
//    }

    public void test(MemberProfile memberProfile, ExArticle exArticle, String redirecturl) {
        String topicName = "test"; // 알림 전용 토픽

        try {

            // 세 개의 값을 JSON 객체로 생성
            BidNotification messagePayload = BidNotification.builder().
                    userId(memberProfile.getId())
                    .articleId(exArticle.getId())
                    .redirectUrl(redirecturl)
                    .build();

//            BidNotification(memberProfile, exArticle, redirecturl);


            String message = objectMapper.writeValueAsString(messagePayload); // JSON으로 직렬화

            CompletableFuture<SendResult<String, String>> future = kafkaTemplatetest.send(topicName, message);

            future
                    .thenAccept(result -> {
                        log.info("Message sent to topic {} with offset {}", topicName, result.getRecordMetadata().offset());
                    })
                    .exceptionally(ex -> {
                        log.error("Failed to send message to topic {} due to {}", topicName, ex.getMessage());
                        return null; // Exceptionally는 Void를 반환하므로 null 반환
                    });
        } catch (Exception e) {
            log.error("Failed to convert message to JSON due to {}", e.getMessage());
        }
    }

}
