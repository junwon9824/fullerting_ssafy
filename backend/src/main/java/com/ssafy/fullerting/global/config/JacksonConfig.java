//package com.ssafy.fullerting.global.config;
//
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule; // Java 8 날짜/시간 지원
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class JacksonConfig {
//
//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        // Java 8 날짜/시간 API 지원
//        objectMapper.registerModule(new JavaTimeModule());
//
//        // 미처리 속성에 대한 예외 발생 안 함
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//        // 추가 설정 (필요 시)
//        // objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // 날짜를 타임스탬프로 직렬화하지 않음
//
//        return objectMapper;
//    }
//}
