package com.ssafy.fullerting.security.model.entity;

import com.ssafy.fullerting.user.model.entity.MemberProfile;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuthAttributes {
    GOOGLE("google", (attributes) -> {
        MemberProfile memberProfile = new MemberProfile();
        memberProfile.setNickname((String) attributes.get("name"));
        memberProfile.setEmail((String) attributes.get("email"));
        return memberProfile;
    }),

    NAVER("naver", (attributes) -> {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        System.out.println(response);
        MemberProfile memberProfile = new MemberProfile();
        memberProfile.setNickname((String) response.get("name"));
        memberProfile.setEmail(((String) response.get("email")));
        return memberProfile;
    }),

    KAKAO("kakao", (attributes) -> {
        // kakao는 kakao_account에 유저정보가 있다. (email)
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        // kakao_account안에 또 profile이라는 JSON객체가 있다. (nickname, profile_image)
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

        MemberProfile memberProfile = new MemberProfile();
        memberProfile.setNickname((String) kakaoProfile.get("nickname"));
        memberProfile.setEmail((String) kakaoAccount.get("email"));
        return memberProfile;
    });

    private final String registrationId;
    private final Function<Map<String, Object>, MemberProfile> of;

    OAuthAttributes(String registrationId, Function<Map<String, Object>, MemberProfile> of) {
        this.registrationId = registrationId;
        this.of = of;
    }

    public static MemberProfile extract(String registrationId, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> registrationId.equals(provider.registrationId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .of.apply(attributes);
    }
}