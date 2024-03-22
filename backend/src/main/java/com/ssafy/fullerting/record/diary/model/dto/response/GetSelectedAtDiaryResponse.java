package com.ssafy.fullerting.record.diary.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.fullerting.record.diary.model.entity.Diary;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class GetSelectedAtDiaryResponse {
    private Long diaryId;
    private String diaryBehavior;
    private String diaryTitle;
    private String diaryContent;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp diaryCreatedAt;

    public static GetSelectedAtDiaryResponse toResponse(Diary diary){
        return GetSelectedAtDiaryResponse.builder()
                .diaryId(diary.getId())
                .diaryBehavior(diary.getBehavior())
                .diaryTitle(diary.getTitle())
                .diaryContent(diary.getContent())
                .diaryCreatedAt(diary.getCreatedAt())
                .build();
    }
}
