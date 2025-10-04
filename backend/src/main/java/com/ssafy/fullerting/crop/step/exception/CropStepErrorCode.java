package com.ssafy.fullerting.crop.step.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
public enum CropStepErrorCode {
    NOT_EXISTS_CROP_STEP("존재하지 않는 단계입니다.", BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}
