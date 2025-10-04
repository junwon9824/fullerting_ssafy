package com.ssafy.fullerting.record.packdiary.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class GetCropStepRequest {
    private String cropTypeName; //작물 종류
    private int cropStepGrowth; //작물 단계
    private Double confidenceScore; //정확도
}
