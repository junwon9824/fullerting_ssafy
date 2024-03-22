package com.ssafy.fullerting.record.packdiary.model.dto.response;

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
}
