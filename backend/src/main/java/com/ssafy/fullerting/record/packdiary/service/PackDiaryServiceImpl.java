package com.ssafy.fullerting.record.packdiary.service;

import com.ssafy.fullerting.crop.type.model.entity.Crop;
import com.ssafy.fullerting.crop.type.model.entity.enums.CropType;
import com.ssafy.fullerting.crop.type.repository.CropTypeRepository;
import com.ssafy.fullerting.record.packdiary.exception.PackDiaryErrorCode;
import com.ssafy.fullerting.record.packdiary.exception.PackDiaryException;
import com.ssafy.fullerting.record.packdiary.model.dto.request.CreatePackDiaryRequest;
import com.ssafy.fullerting.record.packdiary.model.entity.PackDiary;
import com.ssafy.fullerting.record.packdiary.repository.PackDiaryRepository;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.ssafy.fullerting.record.packdiary.exception.PackDiaryErrorCode.NOT_EXISTS_CROP;

@RequiredArgsConstructor
@Service
@Slf4j
public class PackDiaryServiceImpl implements PackDiaryService {
    private final PackDiaryRepository packDiaryRepository;
    private final CropTypeRepository cropTypeRepository;

    @Override
    public void createPackDiary(CustomUser user, CreatePackDiaryRequest createPackDiaryRequest) {
        Crop crop = cropTypeRepository.findById(createPackDiaryRequest.getCropTypeId()).orElseThrow(()->new PackDiaryException(NOT_EXISTS_CROP));
        packDiaryRepository.save(PackDiary.builder()
                .id(null)
                .user(user)
                .crop(crop)
                .title(createPackDiaryRequest.getPackDiaryTitle())
                .culStartAt(createPackDiaryRequest.getPackDiaryCulStartAt())
                .culEndAt(null)
                .growthStep(0)
                .createdAt(null)
                .build()
        );
    }
}
