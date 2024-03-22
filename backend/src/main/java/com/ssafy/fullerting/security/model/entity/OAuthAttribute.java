package com.ssafy.fullerting.security.model.entity;

import com.ssafy.fullerting.user.model.entity.CustomUser;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class OAuthAttribute {
    // 인증서버로 받은 사용자 정보를 담는 컨테이너

    private Map<String, Object> attributes;
    private String userNameAttributeName;
    private String email;
    private String name;
    private String nickname; // 구글 정보에 직접적인 닉네임이 없으므로, 필요에 따라 name에서 파생시킬 수 있음
    private String thumbnail;
    private String authProvider;


    public static OAuthAttribute ofGoogle(Map<String, Object> attributes) {
        return OAuthAttribute.builder()
                .email((String) attributes.get("email"))
                .name((String) attributes.get("name"))
                // 닉네임 설정, 구글 API에서 직접 제공하지 않으므로 name에서 파생시키거나 다른 로직 적용
                .nickname((String) attributes.get("name") + "-google") // 예시로 name을 닉네임으로 사용
                .authProvider("google")
                .attributes(attributes)
                .userNameAttributeName("sub") // 구글의 고유 식별자 키는 "sub"
                .build();
    }

    public static OAuthAttribute of(String registrationId, Map<String, Object> attributes) {
        // 제공자별 처리를 확장할 수 있도록 구조를 만듭니다.
        if ("google".equals(registrationId)) {
            return ofGoogle(attributes);
        }
        // 필요한 경우 여기에 다른 제공자에 대한 처리를 추가할 수 있습니다.
        return null;
    }

    // OAuthAttribute 객체의 속성을 Map으로 변환하는 메소드
    public Map<String, Object> convertToMap() {
        Map<String, Object> map = new HashMap<>();
        map.putAll(this.attributes); // 기본적으로 받은 attributes를 모두 넣음
        map.put("email", this.email);
        map.put("name", this.name);
        map.put("nickname", this.nickname);
        map.put("thumbnail", this.thumbnail);
        map.put("authProvider", this.authProvider);
        return map;
    }

}
