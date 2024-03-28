package com.ssafy.fullerting.community.comment.controller;

import com.ssafy.fullerting.community.comment.model.dto.request.RegisterCommentRequest;
import com.ssafy.fullerting.community.comment.model.dto.response.CommentResonse;
import com.ssafy.fullerting.community.comment.service.CommentService;
import com.ssafy.fullerting.global.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/articles")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{article_id}/comments")
    public ResponseEntity<MessageUtils> registcomment(@RequestBody RegisterCommentRequest registerCommentRequest, @PathVariable Long article_id) { //댓글등록

        commentService.registcomment(article_id, registerCommentRequest);
        return ResponseEntity.ok(MessageUtils.success());

    }

    @GetMapping("/comments/all")
    public ResponseEntity<MessageUtils> allcomment() {

        List<CommentResonse> commentResonses = commentService.allcomment();
        return ResponseEntity.ok(MessageUtils.success(commentResonses));

    }

    @DeleteMapping("/{article_id}/comments/{comment_id}")
    public ResponseEntity<MessageUtils> deletecommentbyid(@PathVariable Long article_id
            , @PathVariable Long comment_id) {
        commentService.deletecommentbyid(article_id, comment_id);

        return ResponseEntity.ok(MessageUtils.success());

    }
}
