package com.ssafy.fullerting.record.diary.service;

import com.ssafy.fullerting.record.diary.model.dto.response.GetAllDiaryResponse;

import java.util.List;

public interface DiaryService {
    List<GetAllDiaryResponse> getAllDiary(Long packDiaryId);
}
