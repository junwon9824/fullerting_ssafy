package com.ssafy.fullerting.deal.controller;

import com.ssafy.fullerting.deal.service.DealService;
import com.ssafy.fullerting.global.utils.MessageUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/exchanges")
@Tag(name = "제안 기능 API", description = "제안과 관련된 기능 제공")
public class DealController {

    private final DealService dealService;


    @GetMapping("/category/deal")
    @Operation(summary = "제안 카테고리만 조회하기 ", description = "제안  카테고리 조회하기 ")
    public ResponseEntity<MessageUtils> selectDeals() {

        log.info("[selectFavorite  ]: {}");
        return ResponseEntity.ok().body(MessageUtils.success(dealService.selectDeals()));

    }


    @GetMapping("/mybidarticles")
    @Operation(summary = "내가 입찰 제안한 게시물 조회 ", description = "내가 입찰 제안한 게시물 조회")
    public ResponseEntity<MessageUtils> mybidarticles() {

        log.info("[selectFavorite  ]: {}");
        return ResponseEntity.ok().body(MessageUtils.success(dealService.mybidarticles()));

    }

    @GetMapping("/wrotearticles")
    @Operation(summary = "내가 작성한 게시물 조회 ", description = "내가 작성한 게시물 조회")
    public ResponseEntity<MessageUtils> wrotearticles() {

        log.info("[wrotearticles  ]: {}");
        return ResponseEntity.ok().body(MessageUtils.success(dealService.wrotearticles()));

    }
}
