package com.ssafy.fullerting.exArticle.controller;

import com.ssafy.fullerting.exArticle.model.dto.request.ExArticleDoneRequest;
import com.ssafy.fullerting.exArticle.model.dto.request.ExArticleRegisterRequest;
import com.ssafy.fullerting.exArticle.model.dto.response.ExArticleAllResponse;
import com.ssafy.fullerting.exArticle.model.dto.response.ExArticleDetailResponse;
import com.ssafy.fullerting.exArticle.model.dto.response.ExArticleKeywordResponse;
import com.ssafy.fullerting.exArticle.model.dto.response.ExArticleResponse;
import com.ssafy.fullerting.exArticle.service.ExArticleService;
import com.ssafy.fullerting.global.utils.MessageUtils;

import com.ssafy.fullerting.user.model.dto.response.UserResponse;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import com.ssafy.fullerting.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/exchanges")
@Tag(name = "거래 기능 API", description = "거래 기능 제공.")
public class ExArticleController {

    private final ExArticleService exArticleService;
    private final UserService userService;

    @PostMapping("")
    @Operation(summary = "작물 거래 게시물 등록 ", description = "작물등록진행")
    public ResponseEntity<MessageUtils> register(
            @RequestPart(value = "file") List<MultipartFile> file,
                                                 @RequestPart(value = "exArticleRegisterRequest") ExArticleRegisterRequest exArticleRegisterRequest,
                                                 @AuthenticationPrincipal String email) {

        Long exarticleid = exArticleService.register(exArticleRegisterRequest, email, file);
//        Long exarticleid = exArticleService.register(exArticleRegisterRequest, email);

        log.info("[register article ]: {}", exArticleRegisterRequest.toString());

        return ResponseEntity.ok().body(MessageUtils.success(exarticleid));
    }


    @PostMapping("/{ex_article_id}/images")
    @Operation(summary = "작물 거래 게시물 이미지 등록 ", description = "작물 거래 게시물 이미지 등록")
    public ResponseEntity<MessageUtils> registeriamges(@RequestPart(value = "file") List<MultipartFile> file, @PathVariable Long ex_article_id
    ) {
        exArticleService.registeriamges(file, ex_article_id);
//        log.info("[register article ]: {}", );

        return ResponseEntity.ok().body(MessageUtils.success());

    }

    @GetMapping("/all")
    @Operation(summary = "작물거래 전체 조회 ", description = "작물거래 전체 조회")
    public ResponseEntity<MessageUtils> allArticle() {
        List<ExArticleAllResponse> exArticleResponse = exArticleService.allArticle();


        log.info("[all article ]: {}", exArticleResponse);
        return ResponseEntity.ok().body(MessageUtils.success(exArticleResponse));
    }

    @GetMapping("/{ex_article_id}/detail")
    @Operation(summary = "작물거래 상세 조회 ", description = "작물거래 상세 조회")
    public ResponseEntity<MessageUtils> detail(@PathVariable Long ex_article_id) {
        ExArticleDetailResponse detail = exArticleService.detail(ex_article_id);

        log.info("[detail article]: {}", detail);
        return ResponseEntity.ok().body(MessageUtils.success(detail));
    }


    @PostMapping("/{ex_article_id}/like")
    @Operation(summary = "작물거래 좋아요 등록  ", description = "작물거래 좋아요 등록")
    public ResponseEntity<MessageUtils> like(@PathVariable Long ex_article_id) {
        exArticleService.like(ex_article_id);

        log.info("[like article]: {}", ex_article_id);

        return ResponseEntity.ok().body(MessageUtils.success());

    }


    @DeleteMapping("/{ex_article_id}/delete")
    @Operation(summary = "작물거래 좋아요 삭제  ", description = "작물거래 좋아요 삭제 ")
    public ResponseEntity<MessageUtils> deletelike(@PathVariable Long ex_article_id) {
        exArticleService.deletelike(ex_article_id);

        log.info("[delete article]: {}", ex_article_id);
        return ResponseEntity.ok().body(MessageUtils.success());
    }

    @PostMapping("/{ex_article_id}/done")
    @Operation(summary = "작물거래 완료   ", description = "작물거래 완료   ")
    public ResponseEntity<MessageUtils> done(@PathVariable Long ex_article_id, @RequestBody ExArticleDoneRequest exArticleDoneRequest) {

        exArticleService.done(ex_article_id, exArticleDoneRequest);

        log.info("[done article]: {}", ex_article_id);
        return ResponseEntity.ok().body(MessageUtils.success());
    }

    //    작물 거래 게시물 키워드 검색
//    /v1/exchanges/search?keyword={keyword}
    @GetMapping("/search")
    @Operation(summary = "작물 거래 게시물 키워드 검색 ", description = "작물 거래 게시물 키워드 검색")
    public ResponseEntity<MessageUtils> like(@RequestParam String keyword) {
        List<ExArticleKeywordResponse> exArticleResponses = exArticleService.keyword(keyword);

        log.info("[search article keyword]: {}", exArticleResponses);

        return ResponseEntity.ok().body(MessageUtils.success(exArticleResponses));
    }


    @GetMapping("/category/like")
    @Operation(summary = "관심 조회하기 ", description = "관심 카테고리 조회하기 ")
    public ResponseEntity<MessageUtils> selectFavorite() {

        log.info("[selectFavorite  ]: {}");
        return ResponseEntity.ok().body(MessageUtils.success(exArticleService.selectFavorite()));

    }

    @GetMapping("/done")
    @Operation(summary = "나의 종료된 거래 게시물 조회하기 ", description = "나의 종료된 거래 게시물 조회하기 ")
    public ResponseEntity<MessageUtils> finishedarticles() {

        log.info("[finishedarticles  ]: {}");
        return ResponseEntity.ok().body(MessageUtils.success(exArticleService.finishedarticles()));

    }

    @DeleteMapping("/{ex_article_id}")
    @Operation(summary = "나의 작물 거래 게시물 삭제 ", description = "나의 작물 거래 게시물 삭제 ")
    public ResponseEntity<MessageUtils> deletearticle(@PathVariable Long ex_article_id) {

        log.info("[finishedarticles  ]: {}");
        exArticleService.deletearticle(ex_article_id);
        return ResponseEntity.ok().body(MessageUtils.success());

    }
}
