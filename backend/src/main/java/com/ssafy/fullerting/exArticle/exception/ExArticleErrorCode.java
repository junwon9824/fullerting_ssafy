package com.ssafy.fullerting.exArticle.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
public enum ExArticleErrorCode {
    NOT_EXISTS ("존재하지 않는 게시물입니다.", BAD_REQUEST),
    ALREADY_LIKED ("이미 좋아하는 게시물 입니다.", BAD_REQUEST),
    ALREADY_IN ("이미 등록된 게시물입니다.", BAD_REQUEST),
    TRANSACTION_FAIL("트랜젝션에 실패했습니다.", BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}
