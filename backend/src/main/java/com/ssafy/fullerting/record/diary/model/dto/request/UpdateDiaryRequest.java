package com.ssafy.fullerting.record.diary.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class UpdateDiaryRequest {
    private List<Long> images;
    private List<MultipartFile> newImages;
    private String diaryTitle;
    private String diaryContent;
    private LocalDate diarySelectedAt;
}
