package com.ssafy.fullerting.global.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final AuthChannelInterceptor authChannelInterceptor;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub", "/user"); // "/sub"과 "/user"로 시작하는 채널에 대한 구독을 활성화
        config.setApplicationDestinationPrefixes("/pub"); // "/pub"으로 시작하는 메시지는 @MessageMapping 핸들러로 라우팅

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        log.info("registerStompEndpointss");

        registry.addEndpoint("/ws") // WebSocket 엔드포인트 설정 // ex )
                .setAllowedOriginPatterns("*");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(authChannelInterceptor);
    }
}
