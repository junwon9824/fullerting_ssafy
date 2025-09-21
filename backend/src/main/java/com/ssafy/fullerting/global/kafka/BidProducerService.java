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
    private final KafkaTemplate<String, BidNotification> kafkaTemplate;
    private static final String ALARM_TOPIC = "kafka-alarm";
    private final KafkaTemplate<String, BidRequestMessage> bidRequestKafkaTemplate;
    private static final String BID_TOPIC = "bid_requests";
    // �엯李� �떊泥� 硫붿떆吏�瑜� 移댄봽移대줈 蹂대궡�뒗 硫붿꽌�뱶

    public void sendBidRequest(Long exArticleId, int dealCurPrice, MemberProfile bidder) {
        BidRequestMessage bidRequest = new BidRequestMessage(exArticleId, dealCurPrice, bidder.getNickname());
        bidRequestKafkaTemplate.send(BID_TOPIC, exArticleId.toString(), bidRequest);
        log.info("移댄봽移대줈 �엯李� �떊泥��쓣 �쟾�넚�뻽�뒿�땲�떎: {}", bidRequest);
    }

    public void kafkaalarmproduce(MemberProfile memberProfile, ExArticle exArticle, String redirecturl) {
        try {
            BidNotification messagePayload = BidNotification.builder()
                    .userid(memberProfile.getId())
                    .articleid(exArticle.getId())
                    .redirectUrl(redirecturl)
                    .price(exArticle.getDeal() == null ? exArticle.getTrans().getTrans_sell_price()
                            : exArticle.getDeal().getDealCurPrice())
                    .build();

            String key = exArticle.getId().toString();
            kafkaTemplate.send(ALARM_TOPIC, key, messagePayload)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            log.info("Message sent to topic {} with offset {}", ALARM_TOPIC,
                                    result.getRecordMetadata().offset());
                        } else {
                            log.error("Failed to send message to topic {} due to {}", ALARM_TOPIC, ex.getMessage());
                        }
                    });
        } catch (Exception e) {
            log.error("Failed to send kafka alarm: {}", e.getMessage());
        }
    }

}
