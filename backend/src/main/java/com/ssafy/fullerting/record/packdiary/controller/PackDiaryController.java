package com.ssafy.fullerting.record.packdiary.controller;

import com.ssafy.fullerting.global.utils.MessageUtils;
import com.ssafy.fullerting.record.packdiary.model.dto.request.CreatePackDiaryRequest;
import com.ssafy.fullerting.record.packdiary.service.PackDiaryService;
import com.ssafy.fullerting.user.model.dto.response.UserResponse;
import com.ssafy.fullerting.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/pack_diaries")
@RequiredArgsConstructor //생성자 주입
@Slf4j
public class PackDiaryController {
    private final PackDiaryService packDiaryService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity createPackDiary(@RequestBody CreatePackDiaryRequest createPackDiaryRequest){
        log.debug("test : " + createPackDiaryRequest.toString());
        UserResponse userResponse = userService.getUserInfo();
        packDiaryService.createPackDiary(userResponse.toEntity(userResponse), createPackDiaryRequest);
        return ResponseEntity.ok().body(MessageUtils.success());
    }
}
