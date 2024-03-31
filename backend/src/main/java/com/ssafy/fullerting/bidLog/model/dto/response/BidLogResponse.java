package com.ssafy.fullerting.bidLog.model.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class BidLogResponse {
    private Long id;
    private String nickname;
    private String thumbnail;

    private Long userId;
    private LocalDateTime localDateTime;

    private int bidLogPrice;
    private Long exarticleid;
    private  int bidcount;

}
