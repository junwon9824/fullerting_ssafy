package com.ssafy.fullerting.record.packdiary.service;

import com.ssafy.fullerting.record.packdiary.model.dto.request.CreatePackDiaryRequest;
import com.ssafy.fullerting.record.packdiary.model.dto.request.GetCropStepRequest;
import com.ssafy.fullerting.record.packdiary.model.dto.request.UpdatePackDiaryRequest;
import com.ssafy.fullerting.record.packdiary.model.dto.response.GetAllPackDiaryResponse;
import com.ssafy.fullerting.record.packdiary.model.dto.response.GetCropStepResponse;
import com.ssafy.fullerting.record.packdiary.model.dto.response.GetDetailPackDiaryResponse;
import com.ssafy.fullerting.user.model.entity.CustomUser;

import java.util.List;

public interface PackDiaryService {
    void createPackDiary(CustomUser user, CreatePackDiaryRequest createPackDiaryRequest);
    void updatePackDiary(Long packDiaryId, UpdatePackDiaryRequest updatePackDiaryRequest);
    void deletePackDiary(Long packDiaryId);
    List<GetAllPackDiaryResponse> getAllPackDiary();
    GetDetailPackDiaryResponse getDetailPackDiary(Long packDiaryId);
    List<GetAllPackDiaryResponse> searchPackDiary(String keyword);
    void endCropCultivation(Long packDiaryId);
    GetCropStepResponse getCropStep(Long packDiaryId, GetCropStepRequest getCropStepRequest);
}
