package com.ssafy.fullerting.deal.controller;

import com.ssafy.fullerting.deal.model.dto.request.DealProposeRequest;
import com.ssafy.fullerting.deal.service.DealService;
import com.ssafy.fullerting.exArticle.model.dto.request.ExArticleRegisterRequest;
import com.ssafy.fullerting.global.utils.MessageUtils;

import com.ssafy.fullerting.user.model.entity.CustomUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/exchanges")
@Tag(name = "제안 기능 API", description = "제안과 관련된 기능 제공")
public class DealController {

    private final DealService dealService;

    @GetMapping("/{ex_article_id}/suggestion")
    @Operation(summary = "가격 제안조회하기 ", description = "가격 제안 조회 하기")
    public ResponseEntity<MessageUtils> selectdeal(@AuthenticationPrincipal String email) {
        dealService.selectdeal();
        log.info("[show deal]: {}");
        return ResponseEntity.ok().body(MessageUtils.success());

    }





}
