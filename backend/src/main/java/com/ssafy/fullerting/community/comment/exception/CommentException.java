package com.ssafy.fullerting.community.comment.exception;


import com.ssafy.fullerting.community.article.exception.ArticleErrorCode;
import lombok.Getter;

@Getter

public class CommentException extends RuntimeException {

    private final CommentErrorCode commentErrorCode;

    public CommentException(CommentErrorCode CommentErrorCode) {
        super(CommentErrorCode.getMessage());
        this.commentErrorCode = CommentErrorCode;
    }
}
