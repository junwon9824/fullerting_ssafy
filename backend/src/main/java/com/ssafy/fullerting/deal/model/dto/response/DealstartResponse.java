package com.ssafy.fullerting.deal.model.dto.response;

import com.ssafy.fullerting.user.model.dto.response.UserResponse;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DealstartResponse {
    private Long bidLogId;
    private Long exArticleId;
    private UserResponse userResponse;
    private int dealCurPrice;
    private int maxPrice;
    private int bidderCount;
    private String errorMessage;

    public static DealstartResponse error(String message) {
        return DealstartResponse.builder()
                .errorMessage(message)
                .build();
    }
}
