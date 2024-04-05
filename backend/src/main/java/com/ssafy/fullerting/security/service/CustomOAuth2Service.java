package com.ssafy.fullerting.security.service;

import com.ssafy.fullerting.security.model.entity.OAuthAttribute;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest); //OAuth2 정보를 가져온다.

        // OAuth2 제공자(google)의 고유 식별자를 가져온다
        String registrationId = userRequest.getClientRegistration().getRegistrationId();//소셜 정보를 가져온다.
        // 인증서버로부터(구글) 로그인 요청한 사용자 정보를 가져온다
        Map<String, Object> attributeFromOAuth2 = oAuth2User.getAttributes();

        // OAuthAttribute 인스턴스를 생성
        OAuthAttribute oAuthAttribute = OAuthAttribute.of(registrationId, attributeFromOAuth2);

        // 사용자에게 부여할 권한을 설정
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_MEMBER"));

        // DefaultOAuth2User 객체를 생성하여 반환
        return new DefaultOAuth2User(
                authorities,
                oAuthAttribute.convertToMap(),
                "email"
        );
    }
}
