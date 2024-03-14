package com.ssafy.fullerting.exArticle.controller;

import com.ssafy.fullerting.exArticle.model.dto.request.ExArticleRegisterRequest;
import com.ssafy.fullerting.exArticle.service.ExArticleService;
import com.ssafy.fullerting.global.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/exchanges")
@Tag(name = "거래 기능 API", description = "거래 기능 제공.")
public class ExArticleController {

    private final ExArticleService exArticleService;

    @PostMapping("")
    @Operation(summary = "작물등록 ", description = "작물등록진행")
    public ResponseEntity<MessageUtils> register(@RequestBody ExArticleRegisterRequest exArticleRegisterRequest) {
        exArticleService. register(exArticleRegisterRequest);
        log.info("[New User]: {}", exArticleRegisterRequest.toString());
        return ResponseEntity.ok().body(MessageUtils.success());
    }





}
