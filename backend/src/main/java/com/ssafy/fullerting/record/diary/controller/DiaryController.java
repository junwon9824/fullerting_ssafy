package com.ssafy.fullerting.record.diary.controller;

import com.ssafy.fullerting.global.utils.MessageUtils;
import com.ssafy.fullerting.record.diary.model.dto.request.CreateDiaryRequest;
import com.ssafy.fullerting.record.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/diaries")
@RequiredArgsConstructor //생성자 주입
@Slf4j
public class DiaryController {
    private final DiaryService diaryService;

    /**
     * 작물일기 전체조회
     * @param packDiaryId
     * @return
     */
    @GetMapping("/{pack_diary_id}")
    public ResponseEntity<MessageUtils> getAllDiary(@PathVariable("pack_diary_id") Long packDiaryId){
        return ResponseEntity.ok().body(MessageUtils.success(diaryService.getAllDiary(packDiaryId)));
    }

    /**
     * 작물일기 상세조회
     * @param diaryId
     * @return
     */
    @GetMapping("/{diary_id}/detail")
    public ResponseEntity<MessageUtils> getDetailDiary(@PathVariable("diary_id") Long diaryId){
        return ResponseEntity.ok().body(MessageUtils.success(diaryService.getDetailDiary(diaryId)));
    }

    /**
     * 작물일기 작성
     * @param packDiaryId
     * @param createDiaryRequest
     * @return
     */
    @PostMapping("/{pack_diary_id}")
    public ResponseEntity<MessageUtils> createDiary(@PathVariable("pack_diary_id") Long packDiaryId, @RequestBody CreateDiaryRequest createDiaryRequest){
        diaryService.createDiary(packDiaryId, createDiaryRequest);
        return ResponseEntity.ok().body(MessageUtils.success());
    }
}
