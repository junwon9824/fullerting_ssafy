package com.ssafy.fullerting.global.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import com.ssafy.fullerting.global.config.BidNotification;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    // ? �Ϲ� String �޽����� ConsumerFactory
    @Bean
    public ConsumerFactory<String, String> stringConsumerFactory() {
        Map<String, Object> config = commonConfig();
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "string-consumer-group");
        JsonDeserializer<String> deserializer = new JsonDeserializer<>(String.class);
        deserializer.addTrustedPackages("*");
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
    }

    // ? BidRequestMessage�� ConsumerFactory
    @Bean
    public ConsumerFactory<String, BidRequestMessage> bidRequestConsumerFactory() {
        Map<String, Object> config = commonConfig();
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "bid-consumer-group");
        JsonDeserializer<BidRequestMessage> deserializer = new JsonDeserializer<>(BidRequestMessage.class);
        deserializer.addTrustedPackages("com.ssafy.fullerting.global.kafka");
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
    }

    // ? BidNotification�� ConsumerFactory
    @Bean
    public ConsumerFactory<String, BidNotification> bidNotificationConsumerFactory() {
        Map<String, Object> config = commonConfig();
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "bid-notification-group");
        JsonDeserializer<BidNotification> deserializer = new JsonDeserializer<>(BidNotification.class);
        deserializer.addTrustedPackages("com.ssafy.fullerting.global.config"); // ���� ��Ű����!
        deserializer.setRemoveTypeHeaders(false);
        deserializer.setUseTypeMapperForKey(true);
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
    }

    // ? String �޽����� ListenerContainerFactory
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> stringKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(stringConsumerFactory());
        factory.setCommonErrorHandler(defaultErrorHandler());
        return factory;
    }

    // ? BidRequestMessage�� ListenerContainerFactory
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BidRequestMessage> bidKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, BidRequestMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(bidRequestConsumerFactory());
        factory.setCommonErrorHandler(defaultErrorHandler());
        return factory;
    }

    // ? BidNotification�� ListenerContainerFactory
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BidNotification> bidNotificationKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, BidNotification> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(bidNotificationConsumerFactory());
        factory.setCommonErrorHandler(defaultErrorHandler());
        return factory;
    }

    // ? ���� Kafka Consumer ����
    private Map<String, Object> commonConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return config;
    }

    // ? ���� ���� �ڵ鷯
    private DefaultErrorHandler defaultErrorHandler() {
        return new DefaultErrorHandler(
                (record, exception) ->
                        System.err.println("Kafka Error: " + record + ", Exception: " + exception.getMessage()),
                new FixedBackOff(1000L, 2)
        );
    }
}
