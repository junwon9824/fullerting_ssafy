package com.ssafy.fullerting.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

/**
 * 테스트용 Kafka 설정 클래스
 * 테스트 실행 시 필요한 토픽을 자동으로 생성합니다.
 */
@TestConfiguration
@ActiveProfiles("test")
public class TestKafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * 테스트용 Kafka Admin 설정
     */
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configs.put(AdminClientConfig.CLIENT_ID_CONFIG, "test-admin");
        
        return new KafkaAdmin(configs);
    }

    /**
     * 입찰 요청 토픽 생성
     */
    @Bean
    public NewTopic bidRequestsTopic() {
        return TopicBuilder.name("bid_requests")
                .partitions(3)
                .replicas(1)
                .build();
    }

    /**
     * 알림 토픽 생성
     */
    @Bean
    public NewTopic kafkaAlarmTopic() {
        return TopicBuilder.name("kafka-alarm")
                .partitions(3)
                .replicas(1)
                .build();
    }

    /**
     * 테스트용 토픽들 생성
     */
    @Bean
    public NewTopic testTopic() {
        return TopicBuilder.name("test-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
