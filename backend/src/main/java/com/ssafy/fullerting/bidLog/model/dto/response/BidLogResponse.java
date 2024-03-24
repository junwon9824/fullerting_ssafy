package com.ssafy.fullerting.bidLog.model.dto.response;

import com.ssafy.fullerting.deal.model.entity.Deal;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class BidLogResponse {
    private Long id;


    private Long user_id;

    private LocalDateTime localDateTime;

    private int bid_log_price;
    private Long exarticleid;

}
