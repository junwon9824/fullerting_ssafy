package com.ssafy.fullerting.trans.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TransErrorCode {

    NOT_EXISTS("존재하지 않는 거래입니다.",HttpStatus.BAD_REQUEST),
    ALREADY_IN("이미 등록된 거래입니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}
