package com.ssafy.fullerting.record.packdiary.service;

import com.ssafy.fullerting.record.packdiary.model.dto.request.CreatePackDiaryRequest;
import com.ssafy.fullerting.user.model.entity.CustomUser;

public interface PackDiaryService {
    void createPackDiary(CustomUser user, CreatePackDiaryRequest createPackDiaryRequest);
}
