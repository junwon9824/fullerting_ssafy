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
    private int cropStepGrowth;
}
