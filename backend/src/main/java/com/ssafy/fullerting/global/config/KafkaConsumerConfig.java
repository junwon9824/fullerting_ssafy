package com.ssafy.fullerting.global.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    // Consumer 설정
//    @Bean
//    public ConsumerFactory<String, BidNotification> consumerFactory() {
//
//        Map<String, Object> config = new HashMap<>();
//        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        config.put(ConsumerConfig.GROUP_ID_CONFIG, "your-group-id");
//        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//
//
//        JsonDeserializer<BidNotification> jsonDeserializer = new JsonDeserializer<>(BidNotification.class);
//        jsonDeserializer.addTrustedPackages("com.ssafy.fullerting.global.config");
//
//        // ErrorHandlingDeserializer 설정
//        Map<String, Object> valueDeserializerConfig = new HashMap<>();
//        valueDeserializerConfig.put(JsonDeserializer.VALUE_DEFAULT_TYPE, BidNotification.class.getName());
//
//
//        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class.getName());
//        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
//        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, BidNotification.class.getName());
//        config.put(JsonDeserializer.TRUSTED_PACKAGES, "com.ssafy.fullerting.global.config");
//
//
//        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new ErrorHandlingDeserializer<>(new JsonDeserializer<>(BidNotification.class)));
//
//    }
//
//
//    // KafkaListenerContainerFactory 설정
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, BidNotification> kafkaJsonContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, BidNotification> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//
//        // DefaultErrorHandler 설정
//        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
//                (record, exception) -> {
//                    System.err.println("Error processing record: " + record + ", error: " + exception.getMessage());
//                    // 필요시 추가 로직을 여기에 작성
//                },
//                new FixedBackOff(1000L, 2) // 1초 대기 후 2회 재시도
//        );
//
//        factory.setCommonErrorHandler(errorHandler); // 오류 처리기 설정
//
//        return factory;
//    }


    ////////////////
    // Consumer 설정
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {

        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "your-group-id");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);


        JsonDeserializer<String> jsonDeserializer = new JsonDeserializer<>(String.class);
        jsonDeserializer.addTrustedPackages("com.ssafy.fullerting.global.config");

        // ErrorHandlingDeserializer 설정
        Map<String, Object> valueDeserializerConfig = new HashMap<>();
        valueDeserializerConfig.put(JsonDeserializer.VALUE_DEFAULT_TYPE, String.class.getName());


        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class.getName());
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, String.class.getName());
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "com.ssafy.fullerting.global.config");


        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new ErrorHandlingDeserializer<>(new JsonDeserializer<>(String.class)));

    }


    // KafkaListenerContainerFactory 설정
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaJsonContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        // DefaultErrorHandler 설정
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
                (record, exception) -> {
                    System.err.println("Error processing record: " + record + ", error: " + exception.getMessage());
                    // 필요시 추가 로직을 여기에 작성
                },
                new FixedBackOff(1000L, 2) // 1초 대기 후 2회 재시도
        );

        factory.setCommonErrorHandler(errorHandler); // 오류 처리기 설정

        return factory;
    }

}
