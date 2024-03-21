package com.ssafy.fullerting.record.packdiary.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AICropStepResponse {
    @JsonProperty("crop_type")
    private String cropType;
    private String grade;
    @JsonProperty("confidence_score")
    private double confidenceScore;
}
