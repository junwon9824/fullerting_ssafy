package com.ssafy.fullerting.badge.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MyBadgeResponse {

    private Long id;
    private Long userId;
    private String badgeName;
    private String badgeImg;
}
