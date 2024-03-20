package com.ssafy.fullerting.user.model.dto.response;

import com.ssafy.fullerting.user.model.entity.CustomUser;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserResponse {

    private Long id;
    private String email;
    private String role;
    private String nickname;
    private String thumbnail;
    private String rank;
    private String location;

    public static CustomUser toEntity(UserResponse userResponse){
        return  CustomUser.builder()
                .email(userResponse.email)
                .id(userResponse.id)
                .location(userResponse.location)
                .nickname(userResponse.nickname)
                .rank(userResponse.rank)
                .role(userResponse.role)
                .thumbnail(userResponse.thumbnail)
                .build();
    }
}
