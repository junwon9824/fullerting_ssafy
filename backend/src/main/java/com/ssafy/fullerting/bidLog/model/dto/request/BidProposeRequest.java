package com.ssafy.fullerting.bidLog.model.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BidProposeRequest {
    private int dealCurPrice;
    private Long userId ;
//    private LocalDateTime localDateTime;
//    private Long chattingRoomId;
//    private int bidLogPrice;
}
