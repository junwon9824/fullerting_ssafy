package com.ssafy.fullerting.record.diary.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.fullerting.record.diary.model.entity.Diary;
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
public class GetDetailDiaryResponse {
    private Long diaryId;
    private Long packDiaryId;
    private String diaryBehavior;
    private String diaryTitle;
    private String diaryContent;
    private LocalDate diarySelectedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp diaryCreatedAt;

    public static GetDetailDiaryResponse fromResponse(Diary diary){
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
