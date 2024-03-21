package com.ssafy.fullerting.record.diary.service;

import com.ssafy.fullerting.record.diary.model.dto.response.GetAllDiaryResponse;
import com.ssafy.fullerting.record.diary.model.dto.response.GetDetailDiaryResponse;

import java.util.List;

public interface DiaryService {
    List<GetAllDiaryResponse> getAllDiary(Long packDiaryId);
    GetDetailDiaryResponse getDetailDiary(Long diaryId);
}
