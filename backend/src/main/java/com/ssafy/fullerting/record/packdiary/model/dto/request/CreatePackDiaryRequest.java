package com.ssafy.fullerting.record.packdiary.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ssafy.fullerting.crop.type.model.entity.enums.CropType;
import com.ssafy.fullerting.record.packdiary.model.entity.PackDiary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CreatePackDiaryRequest {
    private Long cropTypeId;
    private String packDiaryTitle;
    private LocalDate packDiaryCulStartAt;
}
