package com.ssafy.fullerting.user.model.dto.response;

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
}
