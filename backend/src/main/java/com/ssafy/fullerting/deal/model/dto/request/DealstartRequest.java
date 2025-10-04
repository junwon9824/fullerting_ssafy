package com.ssafy.fullerting.deal.model.dto.request;

import com.ssafy.fullerting.user.model.dto.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DealstartRequest {
    private Long exArticleId; // 가격제안 게시물 id
    private int dealCurPrice; // 입찰자가 제안한 금액
    private String redirectURL; // 판매자(글작성자)가 보는 입찰페이지 URL
}
