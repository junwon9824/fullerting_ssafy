package com.ssafy.fullerting.record.packdiary.model.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ssafy.fullerting.record.packdiary.model.entity.PackDiary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CreatePackDiaryRequest {
    private String cropType;
    private String packDiaryTitle;
    private String packDiaryCulStartAt;
}
