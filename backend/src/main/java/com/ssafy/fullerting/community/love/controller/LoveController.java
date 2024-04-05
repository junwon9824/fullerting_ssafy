package com.ssafy.fullerting.community.love.controller;

import com.ssafy.fullerting.community.love.service.LoveService;
import com.ssafy.fullerting.global.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/articles")
public class LoveController {
    private final LoveService loveService;

    @PostMapping("/{article_id}/like")
    public ResponseEntity<MessageUtils> like(@PathVariable Long article_id) {
        loveService.like(article_id);
        return ResponseEntity.ok(MessageUtils.success());
    }
}
