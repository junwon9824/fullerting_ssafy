package com.ssafy.fullerting.record.packdiary.service;

import com.ssafy.fullerting.record.packdiary.model.dto.request.CreatePackDiaryRequest;
import com.ssafy.fullerting.user.model.entity.User;
import org.springframework.http.ResponseEntity;

public interface PackDiaryService {
    void createPackDiary(User user, CreatePackDiaryRequest createPackDiaryRequest);
}
