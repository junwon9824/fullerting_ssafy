package com.ssafy.fullerting.record.packdiary.service;

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
import com.ssafy.fullerting.record.steplog.model.entity.StepLog;
import com.ssafy.fullerting.record.steplog.repository.CropStepLogRepository;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.fullerting.crop.step.exception.CropStepErrorCode.NOT_EXISTS_CROP_STEP;
import static com.ssafy.fullerting.record.packdiary.exception.PackDiaryErrorCode.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class PackDiaryServiceImpl implements PackDiaryService {
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
        List<PackDiary> packDiaryList = packDiaryRepository.findAll();
        return packDiaryList.stream().map(GetAllPackDiaryResponse::toResponse).collect(Collectors.toList());
    }

    @Override
    public GetDetailPackDiaryResponse getDetailPackDiary(Long packDiaryId) throws RuntimeException {
        PackDiary packDiary = packDiaryRepository.findById(packDiaryId).orElseThrow(()->new PackDiaryException(NOT_EXISTS_PACK_DIARY));
        GetDetailPackDiaryResponse getDetailPackDiaryResponse = GetDetailPackDiaryResponse.toResponse(packDiary);
        //작물 재배일
        if(packDiary.getGrowthStep()!=0){
            Step step = cropStepRepository.findByCropIdAndStep(packDiary.getCrop().getId(), packDiary.getGrowthStep()).orElseThrow(()->new CropStepException(NOT_EXISTS_CROP_STEP));
            getDetailPackDiaryResponse = getDetailPackDiaryResponse.toBuilder().cropGrowDay(step.getHarvestDay()).build();
        }

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
    public GetCropStepResponse getCropStep(Long packDiaryId, GetCropStepRequest getCropStepRequest) {
        //현재 작물일지
        PackDiary packDiary = packDiaryRepository.findById(packDiaryId).orElseThrow(()->new PackDiaryException(NOT_EXISTS_PACK_DIARY));
        //갱신할 작물 단계
        Step step = cropStepRepository.findByCropIdAndStep(packDiary.getCrop().getId(), getCropStepRequest.getCropStepGrowth()).orElseThrow(()->new CropStepException(NOT_EXISTS_CROP_STEP));

        //단계가 갱신된 경우
        if(packDiary.getGrowthStep() < getCropStepRequest.getCropStepGrowth()){
            try {
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
            } catch (Exception e){
                throw new PackDiaryException(TRANSACTION_FAIL);

            }
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

