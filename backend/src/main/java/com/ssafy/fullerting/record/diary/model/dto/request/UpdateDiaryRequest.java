package com.ssafy.fullerting.record.diary.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UpdateDiaryRequest {
    private String diaryTitle;
    private String diaryContent;
    private LocalDate diarySelectedAt;
}
