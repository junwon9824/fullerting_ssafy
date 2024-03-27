package com.ssafy.fullerting.global.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.*;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

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
//                .addInterceptors(new HttpSessionHandshakeInterceptor());
//                .withSockJS(); // SockJS 지원
    }


//    @Override
//    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
//        registration.addDecoratorFactory(new WebSocketHandlerDecoratorFactory() {
//            @Override
//            public WebSocketHandler decorate(WebSocketHandler handler) {
//                return new WebSocketHandlerDecorator(handler) {
//                    @Override
//                    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//                        // CORS 설정
//                        session.getHandshakeHeaders().add("Access-Control-Allow-Origin", "*");
//                        log.info("afterConnectionEstablished");
//                        super.afterConnectionEstablished(session);
//                    }
//                };
//            }
//        });
//    }

}
