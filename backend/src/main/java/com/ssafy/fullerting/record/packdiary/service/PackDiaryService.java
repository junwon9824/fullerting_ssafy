package com.ssafy.fullerting.record.packdiary.service;

import com.ssafy.fullerting.record.packdiary.SerializableMultipartFile;
import com.ssafy.fullerting.record.packdiary.model.dto.request.CreatePackDiaryRequest;
import com.ssafy.fullerting.record.packdiary.model.dto.response.AICropStepResponse;
import com.ssafy.fullerting.record.packdiary.model.dto.response.GetAllPackDiaryResponse;
import com.ssafy.fullerting.record.packdiary.model.dto.response.GetDetailPackDiaryResponse;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PackDiaryService {
    void createPackDiary(CustomUser user, CreatePackDiaryRequest createPackDiaryRequest);
    List<GetAllPackDiaryResponse> getAllPackDiary();
    GetDetailPackDiaryResponse getDetailPackDiary(Long packDiaryId);
    void endCropCultivation(Long packDiaryId);
    AICropStepResponse getCropStep(Long packDiaryId, SerializableMultipartFile imageFile);
}
