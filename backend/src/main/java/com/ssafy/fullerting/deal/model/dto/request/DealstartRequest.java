package com.ssafy.fullerting.deal.model.dto.request;

import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DealstartRequest {

    private Long id;
    private Long userId;
    private int dealCurPrice;
    private LocalDateTime localDateTime;
    private Long chattingRoomId;
    private int bidLogPrice;

}
