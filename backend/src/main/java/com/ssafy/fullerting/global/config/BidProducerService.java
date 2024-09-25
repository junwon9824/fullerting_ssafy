package com.ssafy.fullerting.global.config;

import com.ssafy.fullerting.deal.model.dto.response.DealstartResponse;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BidProducerService {
    private final KafkaTemplate<String, DealstartResponse> kafkaTemplate;

    public BidProducerService(KafkaTemplate<String, DealstartResponse> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendBidMessage(Long exArticleId, DealstartResponse dealstartResponse) {
        String topicName = "bidding-" + exArticleId; // 채팅방 ID에 따라 동적으로 토픽 이름 생성
        kafkaTemplate.send(topicName, String.valueOf(dealstartResponse.getBidLogId()), dealstartResponse);
    }
}
