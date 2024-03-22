package com.ssafy.fullerting.record.packdiary.service;

import com.ssafy.fullerting.crop.step.model.entity.Step;
import com.ssafy.fullerting.crop.step.repository.CropStepRepository;
import com.ssafy.fullerting.crop.type.model.entity.Crop;
import com.ssafy.fullerting.crop.type.repository.CropTypeRepository;
import com.ssafy.fullerting.record.packdiary.SerializableMultipartFile;
import com.ssafy.fullerting.record.packdiary.exception.PackDiaryException;
import com.ssafy.fullerting.record.packdiary.model.dto.request.CreatePackDiaryRequest;
import com.ssafy.fullerting.record.packdiary.model.dto.response.AICropStepResponse;
import com.ssafy.fullerting.record.packdiary.model.dto.response.GetAllPackDiaryResponse;
import com.ssafy.fullerting.record.packdiary.model.dto.response.GetDetailPackDiaryResponse;
import com.ssafy.fullerting.record.packdiary.model.entity.PackDiary;
import com.ssafy.fullerting.record.packdiary.repository.PackDiaryRepository;
import com.ssafy.fullerting.record.steplog.model.entity.StepLog;
import com.ssafy.fullerting.record.steplog.repository.CropStepLogRepository;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.fullerting.record.packdiary.exception.PackDiaryErrorCode.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class PackDiaryServiceImpl implements PackDiaryService {
    private final PackDiaryRepository packDiaryRepository;
    private final CropTypeRepository cropTypeRepository;
    private final CropStepRepository cropStepRepository;
    private final CropStepLogRepository cropStepLogRepository;
    private final WebClientService webClientService;

    @Override
    public void createPackDiary(CustomUser user, CreatePackDiaryRequest createPackDiaryRequest) {
        Crop crop = cropTypeRepository.findById(createPackDiaryRequest.getCropTypeId()).orElseThrow(() -> new PackDiaryException(NOT_EXISTS_CROP));
        try {
            packDiaryRepository.save(PackDiary.builder()
                    .user(user)
                    .crop(crop)
                    .title(createPackDiaryRequest.getPackDiaryTitle())
                    .culStartAt(createPackDiaryRequest.getPackDiaryCulStartAt())
                    .culEndAt(null)
                    .growthStep(0)
                    .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                    .build()
            );
        } catch (Exception e) {
            throw new PackDiaryException(TRANSACTION_FAIL);
        }
    }

    @Override
    public List<GetAllPackDiaryResponse> getAllPackDiary() {
        List<PackDiary> packDiaryList = packDiaryRepository.findAll();
        return packDiaryList.stream().map(GetAllPackDiaryResponse::fromResponse).collect(Collectors.toList());
    }

    @Override
    public GetDetailPackDiaryResponse getDetailPackDiary(Long packDiaryId) {
        PackDiary packDiary = packDiaryRepository.findById(packDiaryId).orElseThrow(() -> new PackDiaryException(NOT_EXISTS_PACK_DIARY));
        GetDetailPackDiaryResponse getDetailPackDiaryResponse = GetDetailPackDiaryResponse.fromResponse(packDiary);

        return getDetailPackDiaryResponse;
    }

    @Override
    public void endCropCultivation(Long packDiaryId) {
        PackDiary packDiary = packDiaryRepository.findById(packDiaryId).orElseThrow(() -> new PackDiaryException(NOT_EXISTS_PACK_DIARY));
        packDiary = packDiary.toBuilder()
                .culEndAt(LocalDate.now())
                .build();
        try {
            packDiaryRepository.save(packDiary);
        } catch (Exception e) {
            throw new PackDiaryException(TRANSACTION_FAIL);
        }
    }

    @Override
    public AICropStepResponse getCropStep(Long packDiaryId, SerializableMultipartFile imageFile) {
        Mono<AICropStepResponse> aiCropStepResponse = webClientService.callAIApi(imageFile);

//        return null;

        //현재 작물일지
        PackDiary packDiary = packDiaryRepository.findById(packDiaryId).orElseThrow(()->new PackDiaryException(NOT_EXISTS_PACK_DIARY));
        //갱신할 작물 단계
        Step step = cropStepRepository.findByCropIdAndStep(packDiary.getCrop().getId(), Integer.parseInt(aiCropStepResponse.block().getGrade())).orElseThrow();
        System.out.println("단계!!!!!!!!!!!!!! :" + Integer.parseInt(aiCropStepResponse.block().getGrade()));
        //단계가 갱신된 경우
        if(packDiary.getGrowthStep() < Integer.parseInt(aiCropStepResponse.block().getGrade())){
            //작물일지 단계 갱신
            packDiaryRepository.save(packDiary.toBuilder()
                    .growthStep(step.getStep())
                    .build());

            //작물단계 갱신기록 저장
            cropStepLogRepository.save(StepLog.builder()
                    .packDiary(packDiary)
                    .step(step)
                    .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                    .build());


            return  AICropStepResponse.builder()
                    .cropType(packDiary.getCrop().getName())
                    .build();
        }
        //단계가 갱신되지 않은 경우
        else {
            return  AICropStepResponse.builder()
                    .cropType(packDiary.getCrop().getName())
                    .build();
        }
    }
}

