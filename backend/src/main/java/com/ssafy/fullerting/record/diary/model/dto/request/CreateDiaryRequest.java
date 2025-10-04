package com.ssafy.fullerting.record.diary.model.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class CreateDiaryRequest {
    private List<MultipartFile> images;
    private String diaryTitle;
    private String diaryContent;
    private LocalDate diarySelectedAt;
}
