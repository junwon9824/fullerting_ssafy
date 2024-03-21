package com.ssafy.fullerting.record.diary.controller;

import com.ssafy.fullerting.global.utils.MessageUtils;
import com.ssafy.fullerting.record.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
