package com.ssafy.fullerting.community.comment.controller;

import com.ssafy.fullerting.community.comment.service.CommentService;
import com.ssafy.fullerting.global.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/articles")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{article_id}/comments")
    public ResponseEntity<MessageUtils> registcomment(@PathVariable Long article_id) { //댓글등록

        commentService.registcomment(article_id);
        return ResponseEntity.ok(MessageUtils.success());

    }

}
