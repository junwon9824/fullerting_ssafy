package com.ssafy.fullerting.global.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ssafy.fullerting.deal.model.dto.response.DealstartResponse;
import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.global.config.BidNotification;
import com.ssafy.fullerting.user.model.entity.MemberProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
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

    private static final String BID_TOPIC = "bid_requests";
    // 입찰 신청 메시지를 카프카로 보내는 메서드
    public void sendBidRequest(Long exArticleId, int dealCurPrice, MemberProfile bidder) throws JsonProcessingException {
        BidRequestMessage bidRequest = new BidRequestMessage(exArticleId, dealCurPrice, bidder.getNickname());
        String messageJson = objectMapper.writeValueAsString(bidRequest); // JSON 직렬화

        kafkaTemplatetest.send(BID_TOPIC, exArticleId.toString(), messageJson);

        log.info("카프카로 입찰 신청을 전송했습니다: {}", bidRequest);
    }


    public void kafkaalarmproduce(MemberProfile memberProfile, ExArticle exArticle, String redirecturl) {
        String topicName = "kafka-alarm"; // 알림 전용 토픽

        try {
            Deal deal = exArticle.getDeal();
            if (deal != null) {
                log.info("Deal current price: {}", deal.getDealCurPrice());
            } else {
                log.info("Deal is null");
            }

            // 세 개의 값을 JSON 객체로 생성
            BidNotification messagePayload = BidNotification.builder().
                    userId(memberProfile.getId())
                    .articleId(exArticle.getId())
                    .redirectUrl(redirecturl)
                    .price(exArticle.getDeal() == null ? exArticle.getTrans().getTrans_sell_price() : exArticle.getDeal().getDealCurPrice())
                    .build();

//            BidNotification(memberProfile, exArticle, redirecturl);


            String message = objectMapper.writeValueAsString(messagePayload); // JSON으로 직렬화
            log.info("kafka messagemessagemessage"+ message);
            long startTime = System.currentTimeMillis();

//            CompletableFuture<SendResult<String, String>> future = kafkaTemplatetest.send(topicName, message);
            String key = exArticle.getId().toString();  // 게시물 ID를 키로 사용
            CompletableFuture<SendResult<String, String>> future = kafkaTemplatetest.send(topicName, key, message);

            future
                    .thenAccept(result -> {
                        long endTime = System.currentTimeMillis();
                        log.info("Message sent to topic {} with offset {} in {} ms", topicName, result.getRecordMetadata().offset(), (endTime - startTime));
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
