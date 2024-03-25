package com.ssafy.fullerting.badge.controller;

import com.ssafy.fullerting.badge.service.MyBadgeService;
import com.ssafy.fullerting.global.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/badges")
@Tag(name = "뱃지 기능 API", description = "뱃지와 관련된 기능 제공")
public class BadgeController {

    private final MyBadgeService myBadgeService;

    @GetMapping()
    @Operation(summary = "나의 뱃지 전체 조회", description = "사용자가 보유한 모든 뱃지를 조회한다. <br> [header] accessToken 이용")
    public ResponseEntity<MessageUtils> getBadges() {
        return ResponseEntity.ok().body(MessageUtils.success(myBadgeService.getAllMyBadges()));
    }

}
