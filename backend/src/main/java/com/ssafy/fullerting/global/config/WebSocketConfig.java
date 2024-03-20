package com.ssafy.fullerting.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.*;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub","/user"); // "/sub"과 "/user"로 시작하는 채널에 대한 구독을 활성화
        config.setApplicationDestinationPrefixes("/pub"); // "/pub"으로 시작하는 메시지는 @MessageMapping 핸들러로 라우팅
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chattings") // WebSocket 엔드포인트 설정
                .setAllowedOriginPatterns("*")
                .withSockJS(); // SockJS 지원
    }
}
