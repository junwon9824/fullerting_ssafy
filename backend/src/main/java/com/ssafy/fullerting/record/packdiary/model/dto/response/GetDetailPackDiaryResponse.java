package com.ssafy.fullerting.record.packdiary.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.fullerting.crop.type.model.entity.Crop;
import com.ssafy.fullerting.record.packdiary.model.entity.PackDiary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class GetDetailPackDiaryResponse {
    private Long packDiaryId;
    private long cropTypeId;
    private String cropTypeName;
    private String cropTypeImgUrl;
    private String packDiaryTitle;
    private LocalDate packDiaryCulStartAt;
    private LocalDate packDiaryCulEndAt;
    private int packDiaryGrowthStep;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp packDiaryCreatedAt;
    private Integer cropGrowDay; //작물 재배일

    public static GetDetailPackDiaryResponse fromResponse(PackDiary packDiary){
        return GetDetailPackDiaryResponse.builder()
                .packDiaryId(packDiary.getId())
                .cropTypeId(packDiary.getCrop().getId())
                .cropTypeName(packDiary.getCrop().getName())
                .cropTypeImgUrl(packDiary.getCrop().getImgUrl())
                .packDiaryTitle(packDiary.getTitle())
                .packDiaryCulStartAt(packDiary.getCulStartAt())
                .packDiaryCulEndAt(packDiary.getCulEndAt())
                .packDiaryGrowthStep(packDiary.getGrowthStep())
                .packDiaryCreatedAt(packDiary.getCreatedAt())
                .cropGrowDay(null)
                .build();
    }
}
