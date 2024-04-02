package com.ssafy.fullerting.record.packdiary.service;

import com.ssafy.fullerting.badge.model.entity.Badge;
import com.ssafy.fullerting.badge.model.entity.MyBadge;
import com.ssafy.fullerting.badge.service.BadgeService;
import com.ssafy.fullerting.crop.step.exception.CropStepException;
import com.ssafy.fullerting.crop.step.model.entity.Step;
import com.ssafy.fullerting.crop.step.repository.CropStepRepository;
import com.ssafy.fullerting.crop.type.model.entity.Crop;
import com.ssafy.fullerting.crop.type.repository.CropTypeRepository;
import com.ssafy.fullerting.exArticle.exception.ExArticleErrorCode;
import com.ssafy.fullerting.exArticle.exception.ExArticleException;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.repository.ExArticleRepository;
import com.ssafy.fullerting.global.s3.servcie.AmazonS3Service;
import com.ssafy.fullerting.image.model.entity.Image;
import com.ssafy.fullerting.image.repository.ImageRepository;
import com.ssafy.fullerting.record.diary.model.entity.Diary;
import com.ssafy.fullerting.record.diary.repository.DiaryRepository;
import com.ssafy.fullerting.record.packdiary.exception.PackDiaryException;
import com.ssafy.fullerting.record.packdiary.model.dto.request.CreatePackDiaryRequest;
import com.ssafy.fullerting.record.packdiary.model.dto.request.GetCropStepRequest;
import com.ssafy.fullerting.record.packdiary.model.dto.request.UpdatePackDiaryRequest;
import com.ssafy.fullerting.record.packdiary.model.dto.response.GetAllPackDiaryResponse;
import com.ssafy.fullerting.record.packdiary.model.dto.response.GetCropStepResponse;
import com.ssafy.fullerting.record.packdiary.model.dto.response.GetDetailPackDiaryResponse;
import com.ssafy.fullerting.record.packdiary.model.entity.PackDiary;
import com.ssafy.fullerting.record.packdiary.repository.PackDiaryRepository;
import com.ssafy.fullerting.record.steplog.exception.StepLogErrorCode;
import com.ssafy.fullerting.record.steplog.exception.StepLogException;
import com.ssafy.fullerting.record.steplog.model.entity.StepLog;
import com.ssafy.fullerting.record.steplog.repository.CropStepLogRepository;
import com.ssafy.fullerting.user.model.dto.response.UserResponse;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import com.ssafy.fullerting.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ssafy.fullerting.crop.step.exception.CropStepErrorCode.NOT_EXISTS_CROP_STEP;
import static com.ssafy.fullerting.record.packdiary.exception.PackDiaryErrorCode.*;
import static com.ssafy.fullerting.record.steplog.exception.StepLogErrorCode.NOT_EXISTS_STEP_LOG;
import static java.time.temporal.ChronoUnit.DAYS;

@RequiredArgsConstructor
@Service
@Slf4j
public class PackDiaryServiceImpl implements PackDiaryService {
    private final UserService userService;
    private final PackDiaryRepository packDiaryRepository;
    private final DiaryRepository diaryRepository;
    private final CropTypeRepository cropTypeRepository;
    private final CropStepRepository cropStepRepository;
    private final CropStepLogRepository cropStepLogRepository;
    private final ExArticleRepository exArticleRepository;
    private final ImageRepository imageRepository;
    private final AmazonS3Service amazonS3Service;
    private final BadgeService badgeService;

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
    public void updatePackDiary(Long packDiaryId, UpdatePackDiaryRequest updatePackDiaryRequest) {
        PackDiary packDiary = packDiaryRepository.findById(packDiaryId).orElseThrow(()->new PackDiaryException(NOT_EXISTS_PACK_DIARY));

        try {
            packDiaryRepository.save(packDiary.toBuilder()
                    .title(updatePackDiaryRequest.getPackDiaryTitle())
                    .culStartAt(updatePackDiaryRequest.getPackDiaryCulStartAt())
                    .build());
        } catch(Exception e){
            throw new PackDiaryException(TRANSACTION_FAIL);
        }
    }

    @Override
    public void deletePackDiary(Long packDiaryId) {
        packDiaryRepository.findById(packDiaryId).orElseThrow(()->new PackDiaryException(NOT_EXISTS_PACK_DIARY));
        try {
            //작물일기 삭제
            List<Diary> diaryList = diaryRepository.findAllByPackDiaryId(packDiaryId);
            //작물일기 이미지 삭제
            for(Diary diary : diaryList){
                List<Image> imageList = imageRepository.findAllByDiaryId(diary.getId());
                //S3에서 이미지 삭제
                for(Image image : imageList){
                    amazonS3Service.deleteFile(image.getImgStoreUrl());
                }
                imageRepository.deleteAll(imageList);
            }
            diaryRepository.deleteAll(diaryList);
            //작물단계기록 삭제
            List<StepLog> stepLogList = cropStepLogRepository.findAllByPackDiaryId(packDiaryId);
            cropStepLogRepository.deleteAll(stepLogList);
            //작물 거래 게시글 null 처리
            List<ExArticle> exArticleList = exArticleRepository.findAllByPackDiaryId(packDiaryId);
            for(ExArticle exArticle : exArticleList){
                try {
                    exArticleRepository.save(exArticle.toBuilder()
                            .packDiary(null).build());
                } catch (Exception e){
                    throw new ExArticleException(ExArticleErrorCode.TRANSACTION_FAIL);
                }
            }
            //작물일지 삭제
            packDiaryRepository.deleteById(packDiaryId);
        } catch (Exception e){
            throw new PackDiaryException(TRANSACTION_FAIL);
        }
    }

    @Override
    public List<GetAllPackDiaryResponse> getAllPackDiary() {
        UserResponse userResponse = userService.getUserInfo();
        //본인이 생성한 작물일지 전체조회
        List<PackDiary> packDiaryList = packDiaryRepository.findByUser(UserResponse.toEntity(userResponse));
        return packDiaryList.stream().map(GetAllPackDiaryResponse::toResponse).collect(Collectors.toList());
    }

    @Override
    public GetDetailPackDiaryResponse getDetailPackDiary(Long packDiaryId) throws RuntimeException {
        PackDiary packDiary = packDiaryRepository.findById(packDiaryId).orElseThrow(()->new PackDiaryException(NOT_EXISTS_PACK_DIARY));
        GetDetailPackDiaryResponse getDetailPackDiaryResponse = GetDetailPackDiaryResponse.toResponse(packDiary);
        //작물 재배일
        if(packDiary.getGrowthStep()!=0){
            Step step = cropStepRepository.findByCropIdAndStep(packDiary.getCrop().getId(), packDiary.getGrowthStep()).orElseThrow(()->new CropStepException(NOT_EXISTS_CROP_STEP));
            StepLog stepLog = cropStepLogRepository.findByPackDiaryAndStep(packDiary, step).orElseThrow(()->new StepLogException(NOT_EXISTS_STEP_LOG));
            //단계별 수확날짜 - (오늘 날짜 - 갱신일)
            Integer dif = Math.toIntExact(DAYS.between(stepLog.getUpdatedAt().toLocalDateTime().toLocalDate(), LocalDate.now()));
            //수확일이 지나지 않았을 경우 계산한 디데이 그대로, 지났을 경우 0 반환
            Integer cropGrowDay = (step.getHarvestDay()-dif >= 0) ? step.getHarvestDay()-dif : 0;
            getDetailPackDiaryResponse = getDetailPackDiaryResponse.toBuilder().cropGrowDay(cropGrowDay).build();
        }

        return getDetailPackDiaryResponse;
    }

    @Override
    public List<GetAllPackDiaryResponse> searchPackDiary(String keyword) {
        List<PackDiary> packDiaryList = packDiaryRepository.findByTitle(keyword);
        return packDiaryList.stream().map(GetAllPackDiaryResponse::toResponse).collect(Collectors.toList());
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
    public GetCropStepResponse getCropStep(Long packDiaryId, GetCropStepRequest getCropStepRequest) {
        //현재 작물일지
        PackDiary packDiary = packDiaryRepository.findById(packDiaryId).orElseThrow(()->new PackDiaryException(NOT_EXISTS_PACK_DIARY));
        //갱신할 작물 단계
        Step step = cropStepRepository.findByCropIdAndStep(packDiary.getCrop().getId(), getCropStepRequest.getCropStepGrowth()).orElseThrow(()->new CropStepException(NOT_EXISTS_CROP_STEP));

        //단계 갱신 조건
        // 1. 현재 다이어리의 작물 종류가 인식한 작물 종류와 일치할 경우
        // 2. 인식한 작물 및 단계의 정확도가 n% 이상인 경우
        // 3. 인식한 작물 단계가 현재 다이어리보다 단계가 클 경우
        if(packDiary.getCrop().getName().equals(getCropStepRequest.getCropTypeName())
        && getCropStepRequest.getConfidenceScore() > 0.8
        && packDiary.getGrowthStep() < getCropStepRequest.getCropStepGrowth()){
            try {
                //작물일지 단계 갱신
                packDiary = packDiaryRepository.save(packDiary.toBuilder()
                        .growthStep(step.getStep())
                        .build());

                //작물단계 갱신기록 저장
                cropStepLogRepository.save(StepLog.builder()
                        .packDiary(packDiary)
                        .step(step)
                        .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                        .build());

            } catch (Exception e){
                throw new PackDiaryException(TRANSACTION_FAIL);

            }

            //마지막 단계일 경우 뱃지 생성
            if(getCropStepRequest.getCropStepGrowth() == cropStepRepository.findMaxStepByCropId(packDiary.getCrop().getId())){
                return GetCropStepResponse.builder()
                        .cropTypeName(packDiary.getCrop().getName())
                        .cropStepGrowth(packDiary.getGrowthStep())
                        .cropRenewal(true)
                        .myBadgeResponse(badgeService.earnBadge(packDiary))
                        .build();
            }

            return GetCropStepResponse.builder()
                    .cropTypeName(packDiary.getCrop().getName())
                    .cropStepGrowth(packDiary.getGrowthStep())
                    .cropRenewal(true)
                    .build();
        }
        //단계가 갱신되지 않은 경우
        else {
            return GetCropStepResponse.builder()
                    .cropTypeName(packDiary.getCrop().getName())
                    .cropStepGrowth(packDiary.getGrowthStep())
                    .cropRenewal(false)
                    .build();
        }
    }
}

