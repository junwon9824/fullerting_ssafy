package com.ssafy.fullerting.record.packdiary.service;

import com.ssafy.fullerting.record.packdiary.model.dto.request.CreatePackDiaryRequest;
import com.ssafy.fullerting.record.packdiary.repository.PackDiaryRepository;
import com.ssafy.fullerting.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class PackDiaryServiceImpl implements PackDiaryService {
    private final PackDiaryRepository packDiaryRepository;

    @Override
    public void createPackDiary(User user, CreatePackDiaryRequest createPackDiaryRequest) {
        createPackDiaryRequest.
    }
}
