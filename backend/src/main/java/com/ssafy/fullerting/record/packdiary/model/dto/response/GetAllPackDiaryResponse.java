package com.ssafy.fullerting.record.packdiary.model.dto.response;

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
public class GetAllPackDiaryResponse {
    private Long packDiaryId;
    private String packDiaryTitle;
    private LocalDate packDiaryCulStartAt;
    private LocalDate packDiaryCulEndAt;
    private int packDiaryGrowthStep;
    private Timestamp packDiaryCreatedAt;
    private String cropTypeName;
    private String cropTypeImgUrl;

    public static GetAllPackDiaryResponse fromResponse(PackDiary packDiary){
        return GetAllPackDiaryResponse.builder()
                .packDiaryId(packDiary.getId())
                .packDiaryTitle(packDiary.getTitle())
                .packDiaryCulStartAt(packDiary.getCulStartAt())
                .packDiaryCulEndAt(packDiary.getCulEndAt())
                .packDiaryGrowthStep(packDiary.getGrowthStep())
                .packDiaryCreatedAt(packDiary.getCreatedAt())
                .cropTypeName(packDiary.getCrop().getName())
                .cropTypeImgUrl(packDiary.getCrop().getImgUrl())
                .build();
    }
}
