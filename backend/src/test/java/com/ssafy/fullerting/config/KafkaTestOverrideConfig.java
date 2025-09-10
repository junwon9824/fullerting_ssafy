package com.ssafy.fullerting.config;

import com.ssafy.fullerting.global.kafka.BidRequestMessage;
import com.ssafy.fullerting.global.config.BidNotification;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
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
@SuppressWarnings("removal") // buildProducer/ConsumerProperties 경고 억제(선택)
public class KafkaTestOverrideConfig {

    // Producer: BidRequestMessage
    @Bean @Primary
    public ProducerFactory<String, BidRequestMessage> testBidRequestProducerFactory(KafkaProperties props) {
        Map<String, Object> cfg = props.buildProducerProperties(); // 경고만(컴파일 영향 없음)
        cfg.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        cfg.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(cfg);
    }

    @Bean @Primary
    public KafkaTemplate<String, BidRequestMessage> testBidRequestKafkaTemplate(
            ProducerFactory<String, BidRequestMessage> pf) {
        return new KafkaTemplate<>(pf);
    }

    // Producer: BidNotification
    @Bean @Primary
    public ProducerFactory<String, BidNotification> testBidNotificationProducerFactory(KafkaProperties props) {
        Map<String, Object> cfg = props.buildProducerProperties();
        cfg.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        cfg.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(cfg);
    }

    @Bean @Primary
    public KafkaTemplate<String, BidNotification> testBidNotificationKafkaTemplate(
            ProducerFactory<String, BidNotification> pf) {
        return new KafkaTemplate<>(pf);
    }

    // Consumer/ContainerFactory: BidRequestMessage
    @Bean @Primary
    public ConsumerFactory<String, BidRequestMessage> testBidRequestConsumerFactory(KafkaProperties props) {
        Map<String, Object> cfg = props.buildConsumerProperties();
        JsonDeserializer<BidRequestMessage> value = new JsonDeserializer<>(BidRequestMessage.class, false);
        value.addTrustedPackages("com.ssafy.fullerting.global.kafka");
        return new DefaultKafkaConsumerFactory<>(cfg, new StringDeserializer(), value);
    }

    @Bean(name = "bidKafkaListenerContainerFactory") // @KafkaListener(containerFactory=...)와 동일 이름
    @Primary
    public ConcurrentKafkaListenerContainerFactory<String, BidRequestMessage> bidKafkaListenerContainerFactory(
            ConsumerFactory<String, BidRequestMessage> cf) {
        var f = new ConcurrentKafkaListenerContainerFactory<String, BidRequestMessage>();
        f.setConsumerFactory(cf);
        f.getContainerProperties().setMissingTopicsFatal(false);
        return f;
    }
}
