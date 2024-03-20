package com.ssafy.fullerting.deal.model.dto.request;

import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DealstartRequest {

    private Long id;
    private int deal_cur_price;


}
