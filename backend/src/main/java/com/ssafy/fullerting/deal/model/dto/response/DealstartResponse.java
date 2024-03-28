package com.ssafy.fullerting.deal.model.dto.response;

import com.ssafy.fullerting.user.model.dto.response.UserResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DealstartResponse {
    private Long bidLogId; // 입찰제안 ID
    private Long exArticleId; // 가격제안 게시물 id
    private UserResponse userResponse; // 입찰자 ID, 썸네일, 닉네임
    private int dealCurPrice; // 입찰자가 제안한 금액
    private int maxPrice; // 현재 이 경매글의 최고가
    private int bidderCount; // 현재 이 경매에 제안한 사람들의 숫자
}
