package com.ssafy.fullerting.exArticle.controller;

import com.ssafy.fullerting.exArticle.model.dto.request.ExArticleRegisterRequest;
import com.ssafy.fullerting.exArticle.model.dto.response.ExArticleResponse;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.service.ExArticleService;
import com.ssafy.fullerting.global.utils.MessageUtils;

import com.ssafy.fullerting.user.model.entity.CustomUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/exchanges")
@Tag(name = "거래 기능 API", description = "거래 기능 제공.")
public class ExArticleController {

    private final ExArticleService exArticleService;

    @PostMapping("")
    @Operation(summary = "작물 거래 게시물 등록 ", description = "작물등록진행")
    public ResponseEntity<MessageUtils> register(@RequestBody ExArticleRegisterRequest exArticleRegisterRequest, @AuthenticationPrincipal CustomUser user) {

        exArticleService.register(exArticleRegisterRequest, user);
        log.info("[register article ]: {}", exArticleRegisterRequest.toString());

        return ResponseEntity.ok().body(MessageUtils.success());
    }

    @GetMapping("")
    @Operation(summary = "작물거래 전체 조회 ", description = "작물거래 전체 조회")
    public ResponseEntity<MessageUtils> allArticle() {

        List<ExArticleResponse> exArticles = exArticleService.allArticle().stream().
                map(exArticle -> exArticle.fromEntity(exArticle)).collect(Collectors.toList());

        log.info("[all article]: {}", exArticles);
        return ResponseEntity.ok().body(MessageUtils.success(exArticles));
    }

}
