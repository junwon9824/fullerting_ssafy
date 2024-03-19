package com.ssafy.fullerting.record.packdiary.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
public enum PackDiaryErrorCode {
    NOT_EXISTS_CROP("존재하지 않는 작물입니다.", BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}
