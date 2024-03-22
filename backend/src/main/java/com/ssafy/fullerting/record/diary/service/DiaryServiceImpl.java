package com.ssafy.fullerting.record.diary.service;

import com.ssafy.fullerting.record.diary.exception.DiaryException;
import com.ssafy.fullerting.record.diary.model.dto.request.CreateDiaryRequest;
import com.ssafy.fullerting.record.diary.model.dto.response.GetAllDiaryResponse;
import com.ssafy.fullerting.record.diary.model.dto.response.GetDetailDiaryResponse;
import com.ssafy.fullerting.record.diary.model.dto.response.GetSelectedAtDiaryResponse;
import com.ssafy.fullerting.record.diary.model.entity.Diary;
import com.ssafy.fullerting.record.diary.repository.DiaryRepository;
import com.ssafy.fullerting.record.packdiary.exception.PackDiaryErrorCode;
import com.ssafy.fullerting.record.packdiary.exception.PackDiaryException;
import com.ssafy.fullerting.record.packdiary.model.entity.PackDiary;
import com.ssafy.fullerting.record.packdiary.repository.PackDiaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
                            .map(GetSelectedAtDiaryResponse::toResponse)
                            .collect(Collectors.toList());
                    response.setGetSelectedAtDiaryResponse(selectedAtDiaryResponseList);
                    return response;
                })
                .sorted(Comparator.comparing(GetAllDiaryResponse::getDiarySelectedAt).reversed()) //날짜 최신순 정렬
                .collect(Collectors.toList());
    }

    @Override
    public GetDetailDiaryResponse getDetailDiary(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(()->new DiaryException(NOT_EXISTS_DIARY));
        GetDetailDiaryResponse getDetailDiaryResponse = GetDetailDiaryResponse.toResponse(diary);

        return getDetailDiaryResponse;
    }

    @Override
    public void createDiary(Long packDiaryId, CreateDiaryRequest createDiaryRequest) {
        try {
            PackDiary packDiary = packDiaryRepository.findById(packDiaryId).orElseThrow(()->new PackDiaryException(NOT_EXISTS_PACK_DIARY));
            if(createDiaryRequest.getDiaryBehavior().equals("다이어리")){
                diaryRepository.save(Diary.builder()
                        .packDiary(packDiary)
                        .behavior("다이어리")
                        .title(createDiaryRequest.getDiaryTitle())
                        .content(createDiaryRequest.getDiaryContent())
                        .selectedAt(createDiaryRequest.getDiarySelectedAt())
                        .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                        .build()
                );
            }
            else {

            }
        } catch (Exception e){
            throw new DiaryException(TRANSACTION_FAIL);
        }
    }
}
