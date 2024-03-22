package com.ssafy.fullerting.record.diary.service;

import com.ssafy.fullerting.record.diary.exception.DiaryException;
import com.ssafy.fullerting.record.diary.model.dto.response.GetAllDiaryResponse;
import com.ssafy.fullerting.record.diary.model.dto.response.GetDetailDiaryResponse;
import com.ssafy.fullerting.record.diary.model.dto.response.GetSelectedAtDiaryResponse;
import com.ssafy.fullerting.record.diary.model.entity.Diary;
import com.ssafy.fullerting.record.diary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ssafy.fullerting.record.diary.exception.DiaryErrorCode.NOT_EXISTS_DIARY;

@RequiredArgsConstructor
@Service
@Slf4j
public class DiaryServiceImpl implements DiaryService{
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
                            .map(GetSelectedAtDiaryResponse::fromResponse)
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
        GetDetailDiaryResponse getDetailDiaryResponse = GetDetailDiaryResponse.fromResponse(diary);

        return getDetailDiaryResponse;
    }
}
