package com.ssafy.fullerting.record.diary.service;

import com.ssafy.fullerting.global.s3.model.entity.response.S3ManyFilesResponse;
import com.ssafy.fullerting.global.s3.servcie.AmazonS3Service;
import com.ssafy.fullerting.image.model.dto.response.ImageResponse;
import com.ssafy.fullerting.image.model.entity.Image;
import com.ssafy.fullerting.image.repository.ImageRepository;
import com.ssafy.fullerting.record.diary.exception.DiaryException;
import com.ssafy.fullerting.record.diary.model.dto.request.CreateDiaryRequest;
import com.ssafy.fullerting.record.diary.model.dto.request.UpdateDiaryRequest;
import com.ssafy.fullerting.record.diary.model.dto.request.WateringCropsRequest;
import com.ssafy.fullerting.record.diary.model.dto.response.GetAllDiaryResponse;
import com.ssafy.fullerting.record.diary.model.dto.response.GetDetailDiaryResponse;
import com.ssafy.fullerting.record.diary.model.dto.response.GetSelectedAtDiaryResponse;
import com.ssafy.fullerting.record.diary.model.entity.Diary;
import com.ssafy.fullerting.record.diary.model.entity.enums.DiaryBehavior;
import com.ssafy.fullerting.record.diary.repository.DiaryRepository;
import com.ssafy.fullerting.record.packdiary.exception.PackDiaryErrorCode;
import com.ssafy.fullerting.record.packdiary.exception.PackDiaryException;
import com.ssafy.fullerting.record.packdiary.model.entity.PackDiary;
import com.ssafy.fullerting.record.packdiary.repository.PackDiaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ssafy.fullerting.record.diary.exception.DiaryErrorCode.NOT_EXISTS_DIARY;
import static com.ssafy.fullerting.record.diary.exception.DiaryErrorCode.TRANSACTION_FAIL;
import static com.ssafy.fullerting.record.packdiary.exception.PackDiaryErrorCode.NOT_EXISTS_PACK_DIARY;

@RequiredArgsConstructor
@Service
@Slf4j
public class DiaryServiceImpl implements DiaryService{
    private final PackDiaryRepository packDiaryRepository;
    private final DiaryRepository diaryRepository;
    private final AmazonS3Service amazonS3Service;
    private final ImageRepository imageRepository;

    @Override
    public List<GetAllDiaryResponse> getAllDiary(Long packDiaryId) {
        List<Diary> diaryList = diaryRepository.findAllByPackDiaryId(packDiaryId);

        Map<String, List<Diary>> groupedDiaries = diaryList.stream()
                .collect(Collectors.groupingBy(diary -> diary.getSelectedAt().toString())); //날짜별로 그룹핑

        return groupedDiaries.entrySet().stream()
                .map(entry -> {
                    GetAllDiaryResponse response = new GetAllDiaryResponse();
                    response.setDiarySelectedAt(entry.getKey());
                    List<GetSelectedAtDiaryResponse> selectedAtDiaryResponseList = entry.getValue().stream()
                            .map(diary -> {
                                GetSelectedAtDiaryResponse selectedAtDiaryResponse = GetSelectedAtDiaryResponse.toResponse(diary);
                                List<ImageResponse> imageResponseList = getImageResponses(diary.getId()); //이미지 가져오기
                                selectedAtDiaryResponse.setImageResponseList(imageResponseList);
                                return selectedAtDiaryResponse;
                            })
                            .collect(Collectors.toList());
                    response.setGetSelectedAtDiaryResponse(selectedAtDiaryResponseList);
                    return response;
                })
                .sorted(Comparator.comparing(GetAllDiaryResponse::getDiarySelectedAt).reversed()) //날짜 최신순 정렬
                .collect(Collectors.toList());
    }

    private List<ImageResponse> getImageResponses(Long diaryId) {
        List<Image> imageList = imageRepository.findAllByDiaryId(diaryId); // diaryId에 해당하는 이미지 목록 조회

        return imageList.stream().map(Image::toResponse).collect(Collectors.toList());
    }

    @Override
    public GetDetailDiaryResponse getDetailDiary(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(()->new DiaryException(NOT_EXISTS_DIARY));
        GetDetailDiaryResponse getDetailDiaryResponse = GetDetailDiaryResponse.toResponse(diary)
                .toBuilder().imageResponseList(getImageResponses(diaryId)).build();
        return getDetailDiaryResponse;
    }

    @Override
    public void createDiary(Long packDiaryId, CreateDiaryRequest createDiaryRequest) {
        PackDiary packDiary = packDiaryRepository.findById(packDiaryId).orElseThrow(()->new PackDiaryException(NOT_EXISTS_PACK_DIARY));
        try {
            Diary diary = diaryRepository.save(Diary.builder()
                    .packDiary(packDiary)
                    .behavior(String.valueOf(DiaryBehavior.다이어리))
                    .title(createDiaryRequest.getDiaryTitle())
                    .content(createDiaryRequest.getDiaryContent())
                    .selectedAt(createDiaryRequest.getDiarySelectedAt())
                    .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                    .build()
            );

            List<MultipartFile> multipartFileList = createDiaryRequest.getImages();
            //이미지 비어있지 않은 경우
            if(multipartFileList.get(0)!=null && !multipartFileList.get(0).isEmpty()){
                //S3에 이미지 업로드
                S3ManyFilesResponse response = amazonS3Service.uploadFiles(multipartFileList);
                //이미지 DB 저장
                response.getUrls().entrySet().stream().map(stringStringEntry -> {
                    Image image = imageRepository.save(Image.builder()
                            .imgStoreUrl(stringStringEntry.getValue())
                            .diary(diary)
                            .build());
                    return image;
                }).collect(Collectors.toList());
            }

        } catch(Exception e){
            e.printStackTrace();
            throw new DiaryException(TRANSACTION_FAIL);
        }
    }

    @Override
    public void updateDiary(Long diaryId, UpdateDiaryRequest updateDiaryRequest) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(()->new DiaryException(NOT_EXISTS_DIARY));

        List<Image> imageList = imageRepository.findAllByDiaryId(diaryId);
        for(Image image : imageList){
            //유지할 이미지가 아닌 경우
            if(!updateDiaryRequest.getImages().contains(image.getId())) {
                //이미지 삭제
                amazonS3Service.deleteFile(image.getImgStoreUrl());
                imageRepository.delete(image);
            }
        }

        List<MultipartFile> multipartFileList = updateDiaryRequest.getNewImages();
        //이미지 비어있지 않은 경우
        if(!multipartFileList.get(0).isEmpty() && multipartFileList.get(0)!=null){
            //이미지 업로드
            S3ManyFilesResponse response = amazonS3Service.uploadFiles(multipartFileList);
            //이미지 DB 저장
            response.getUrls().entrySet().stream().map(stringStringEntry -> {
                Image image = imageRepository.save(Image.builder()
                        .imgStoreUrl(stringStringEntry.getValue())
                        .diary(diary)
                        .build());
                return image;
            }).collect(Collectors.toList());
        }

        //작물일기 수정
        diaryRepository.save(diary.toBuilder()
                .title(updateDiaryRequest.getDiaryTitle())
                .content(updateDiaryRequest.getDiaryContent())
                .build());
    }

    @Override
    public void deleteDiary(Long diaryId) {
       diaryRepository.findById(diaryId).orElseThrow(()->new DiaryException(NOT_EXISTS_DIARY));
        try {
            //이미지 삭제
            List<Image> imageList = imageRepository.findAllByDiaryId(diaryId);
            for (Image image : imageList) {
                amazonS3Service.deleteFile(image.getImgStoreUrl());
            }
            imageRepository.deleteAll(imageList);

            //작물일기 삭제
            diaryRepository.deleteById(diaryId);
        } catch (Exception e){
            throw new DiaryException(TRANSACTION_FAIL);
        }
    }

    @Override
    public void wateringCrops(Long packDiaryId, WateringCropsRequest wateringCropsRequest) {
        PackDiary packDiary = packDiaryRepository.findById(packDiaryId).orElseThrow(()->new PackDiaryException(NOT_EXISTS_PACK_DIARY));
        try {
            diaryRepository.save(Diary.builder()
                    .packDiary(packDiary)
                    .behavior(String.valueOf(DiaryBehavior.물주기))
                    .selectedAt(wateringCropsRequest.getDiarySelectedAt())
                    .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                    .build());
        } catch (Exception e){
            throw new PackDiaryException(NOT_EXISTS_PACK_DIARY);
        }
    }
}
