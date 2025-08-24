package com.ssafy.fullerting.config;

import com.ssafy.fullerting.global.config.BidNotification;
import com.ssafy.fullerting.global.kafka.BidRequestMessage;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@TestConfiguration
public class KafkaTestOverrideConfig {

    // Producer (test.yml의 bootstrap-servers=localhost:9094 적용)
    @Bean
    @Primary
    public ProducerFactory<String, Object> testProducerFactory(KafkaProperties props) {
        Map<String, Object> cfg = props.buildProducerProperties();
        cfg.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        cfg.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(cfg);
    }

    @Bean @Primary
    public KafkaTemplate<String, BidRequestMessage> testBidRequestKafkaTemplate(ProducerFactory<String, Object> pf) {
        return new KafkaTemplate<>(pf);
    }

    @Bean @Primary
    public KafkaTemplate<String, BidNotification> testBidNotificationKafkaTemplate(ProducerFactory<String, Object> pf) {
        return new KafkaTemplate<>(pf);
    }

    // Consumer (BidRequestMessage 전용 예시 — 필요 시 알림 타입도 추가)
    @Bean
    @Primary
    public ConsumerFactory<String, BidRequestMessage> testBidRequestConsumerFactory(KafkaProperties props) {
        Map<String, Object> cfg = props.buildConsumerProperties();
        JsonDeserializer<BidRequestMessage> value = new JsonDeserializer<>(BidRequestMessage.class);
        value.addTrustedPackages("com.ssafy.fullerting.global.kafka");
        return new DefaultKafkaConsumerFactory<>(cfg, new StringDeserializer(), value);
    }

    @Bean
    @Primary
    public ConcurrentKafkaListenerContainerFactory<String, BidRequestMessage> bidKafkaListenerContainerFactory(
            ConsumerFactory<String, BidRequestMessage> cf) {
        ConcurrentKafkaListenerContainerFactory<String, BidRequestMessage> f = new ConcurrentKafkaListenerContainerFactory<>();
        f.setConsumerFactory(cf);
        f.getContainerProperties().setMissingTopicsFatal(false);
        return f;
    }
}
