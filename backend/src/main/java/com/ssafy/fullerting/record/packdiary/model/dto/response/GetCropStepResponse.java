package com.ssafy.fullerting.record.packdiary.model.dto.response;

import com.ssafy.fullerting.badge.model.dto.response.MyBadgeResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class GetCropStepResponse {
    private String cropTypeName;
    private int cropStepGrowth;
    private boolean cropRenewal;
    private MyBadgeResponse myBadgeResponse;
}
