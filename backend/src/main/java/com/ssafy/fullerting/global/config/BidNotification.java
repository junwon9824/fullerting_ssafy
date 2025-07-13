package com.ssafy.fullerting.global.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BidNotification {
    private Long articleid;

    private Long userid;
    private String redirectUrl;
    private Integer price;
    private String localDateTime;
}
