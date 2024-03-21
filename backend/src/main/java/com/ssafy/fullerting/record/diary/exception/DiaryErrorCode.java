package com.ssafy.fullerting.record.diary.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
public enum DiaryErrorCode {
    NOT_EXISTS_DIARY("존재하지 않는 작물일기입니다.", BAD_REQUEST),
    TRANSACTION_FAIL("트랜젝션에 실패했습니다.", BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}
