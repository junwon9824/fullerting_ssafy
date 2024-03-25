package com.ssafy.fullerting.record.diary.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.fullerting.image.model.dto.response.ImageResponse;
import com.ssafy.fullerting.record.diary.model.entity.Diary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
public class GetDetailDiaryResponse {
    private Long diaryId;
    private Long packDiaryId;
    private String diaryBehavior;
    private String diaryTitle;
    private String diaryContent;
    private LocalDate diarySelectedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp diaryCreatedAt;
    private List<ImageResponse> imageResponseList;

    public static GetDetailDiaryResponse toResponse(Diary diary){
        return GetDetailDiaryResponse.builder()
                .diaryId(diary.getId())
                .packDiaryId(diary.getPackDiary().getId())
                .diaryBehavior(diary.getBehavior())
                .diaryTitle(diary.getTitle())
                .diaryContent(diary.getContent())
                .diarySelectedAt(diary.getSelectedAt())
                .diaryCreatedAt(diary.getCreatedAt())
                .build();
    }
}
