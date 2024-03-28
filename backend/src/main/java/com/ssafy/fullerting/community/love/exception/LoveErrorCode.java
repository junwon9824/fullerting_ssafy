package com.ssafy.fullerting.community.love.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LoveErrorCode {

    NOT_EXISTS("존재하지 않는  입니다.",HttpStatus.BAD_REQUEST),
    NOT_MINE("내   댓글 이 아닙니다 .",HttpStatus.BAD_REQUEST),
    ALREADY_IN("이미 등록된 댓글 입니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}
