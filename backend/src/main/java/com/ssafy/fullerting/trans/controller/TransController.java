package com.ssafy.fullerting.trans.controller;

import com.ssafy.fullerting.deal.model.dto.request.DealProposeRequest;
import com.ssafy.fullerting.deal.service.DealService;
import com.ssafy.fullerting.exArticle.model.dto.request.ExArticleRegisterRequest;
import com.ssafy.fullerting.global.utils.MessageUtils;

import com.ssafy.fullerting.trans.service.TransService;
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
@Tag(name = "일반거래 기능 API", description = "일반거래 관련된 기능 제공")
public class TransController {

    private final TransService transService;

    @GetMapping("/category/share")
    @Operation(summary = "나눔 조회하기 ", description = "나눔 조회하기 ")
    public ResponseEntity<MessageUtils> selectAllshare(@AuthenticationPrincipal String email) {

        log.info("[show share]: {}");
        return ResponseEntity.ok().body(MessageUtils.success(transService.selectAllshare()));

    }

    @GetMapping("/category/trans")
    @Operation(summary = "일반거래 조회하기 ", description = "나눔 조회하기 ")
    public ResponseEntity<MessageUtils> selectAlltrans( ) {

        log.info("[show share]: {}");
        return ResponseEntity.ok().body(MessageUtils.success(transService.selectAlltrans()));

    }


    @GetMapping("/category/my/trans")
    @Operation(summary = " 나의 일반거래 조회하기 ", description = "일반거래 카테고리 조회하기 ")
    public ResponseEntity<MessageUtils> selectTrans( ) {

        log.info("[selectFavorite  ]: {}");
        return ResponseEntity.ok().body(MessageUtils.success(transService.selectmyallTrans()));

    }


}
