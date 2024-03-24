package com.ssafy.fullerting.record.packdiary.controller;

import com.ssafy.fullerting.global.utils.MessageUtils;
import com.ssafy.fullerting.record.packdiary.SerializableMultipartFile;
import com.ssafy.fullerting.record.packdiary.model.dto.request.CreatePackDiaryRequest;
import com.ssafy.fullerting.record.packdiary.model.dto.request.GetCropStepRequest;
import com.ssafy.fullerting.record.packdiary.model.dto.request.UpdatePackDiaryRequest;
import com.ssafy.fullerting.record.packdiary.service.PackDiaryService;
import com.ssafy.fullerting.user.model.dto.response.UserResponse;
import com.ssafy.fullerting.user.service.UserService;
import io.lettuce.core.ScriptOutputType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/pack-diaries")
@RequiredArgsConstructor //생성자 주입
@Slf4j
public class PackDiaryController {
    private final PackDiaryService packDiaryService;
    private final UserService userService;

    /**
     * 작물일지 생성
     * @param createPackDiaryRequest
     * @return
     */
    @PostMapping
    public ResponseEntity<MessageUtils> createPackDiary(@RequestBody CreatePackDiaryRequest createPackDiaryRequest){
        UserResponse userResponse = userService.getUserInfo();
        packDiaryService.createPackDiary(userResponse.toEntity(userResponse), createPackDiaryRequest);
        return ResponseEntity.ok().body(MessageUtils.success());
    }

    /**
     * 작물일지 수정
     * @param packDiaryId
     * @param updatePackDiaryRequest
     * @return
     */
    @PatchMapping("/{pack_diary_id}")
    public ResponseEntity<MessageUtils> updatePackDiary(@PathVariable("pack_diary_id") Long packDiaryId, @RequestBody UpdatePackDiaryRequest updatePackDiaryRequest){
        packDiaryService.updatePackDiary(packDiaryId, updatePackDiaryRequest);
        return ResponseEntity.ok().body(MessageUtils.success());
    }

    /**
     * 작물일지 삭제
     * @param packDiaryId
     * @return
     */
    @DeleteMapping("/{pack_diary_id}")
    public ResponseEntity<MessageUtils> deletePackDiary(@PathVariable("pack_diary_id") Long packDiaryId){
        packDiaryService.deletePackDiary(packDiaryId);
        return ResponseEntity.ok().body(MessageUtils.success());
    }


    /**
     * 작물일지 전체조회
     * @return
     */
    @GetMapping
    public ResponseEntity<MessageUtils> getAllPackDiary(){
        return ResponseEntity.ok().body(MessageUtils.success(packDiaryService.getAllPackDiary()));
    }

    /**
     * 작물일지 상세조회
     * @param packDiaryId
     * @return
     */
    @GetMapping("/{pack_diary_id}")
    public ResponseEntity<MessageUtils> getDetailPackDiary(@PathVariable("pack_diary_id") Long packDiaryId){
        return ResponseEntity.ok().body(MessageUtils.success(packDiaryService.getDetailPackDiary(packDiaryId)));
    }

    /**
     * 작물재배 종료
     * @param packDiaryId
     * @return
     */
    @PatchMapping("/{pack_diary_id}/end")
    public ResponseEntity<MessageUtils> endCropCultivation(@PathVariable("pack_diary_id") Long packDiaryId){
        packDiaryService.endCropCultivation(packDiaryId);
        return ResponseEntity.ok().body(MessageUtils.success());
    }

    /**
     * 작물 생육단계 갱신
     * @param packDiaryId
     * @param getCropStepRequest
     * @return
     */
    @PostMapping("/{pack_diary_id}/crop-step")
    public ResponseEntity<MessageUtils> getCropStep(@PathVariable("pack_diary_id") Long packDiaryId, @RequestBody GetCropStepRequest getCropStepRequest){
        return ResponseEntity.ok().body(MessageUtils.success(packDiaryService.getCropStep(packDiaryId, getCropStepRequest)));
    }

}
