package com.ssafy.fullerting.global.config;

import com.ssafy.fullerting.security.model.entity.CustomAuthenticationToken;
import com.ssafy.fullerting.security.util.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Component
public class AuthChannelInterceptor implements ChannelInterceptor {

    private final JwtUtils jwtUtils;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        SimpMessageHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, SimpMessageHeaderAccessor.class);
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String jwtToken = accessor.getFirstNativeHeader("Authorization");

            if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
                String accessToken = jwtToken.substring(7);

                //엑세스 토큰 검증
            Jws<Claims> claimsJws = jwtUtils.validateAccessToken(accessToken);

            //토큰 있으면 검증
            if (claimsJws != null) {
                // 각 권한 문자열을 SimpleGrantedAuthority 객체로 변환
                List<String> roles = claimsJws.getBody().get("authorities", List.class);
                Collection<GrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                CustomAuthenticationToken customAuthenticationToken =
                        new CustomAuthenticationToken(
                                jwtUtils.getEmailByAccessToken(accessToken),
                                jwtUtils.getUserIdByAccessToken(accessToken),
                                null,
                                authorities
                        ); // principal,userid,password,authorities 가 들어감}
                SecurityContextHolder.getContext().setAuthentication(customAuthenticationToken);
                log.info("웹소켓 요청 jwt 토큰 검증 : {}", SecurityContextHolder.getContext().toString());

                // 인증 정보를 웹소켓 세션 속성에 저장
                accessor.getSessionAttributes().put("userAuthentication", customAuthenticationToken);

            }
            }
        }
        return message;
    }
}
