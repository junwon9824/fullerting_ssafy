package com.ssafy.fullerting.community.love.exception;


import lombok.Getter;

@Getter

public class LoveException extends RuntimeException {

    private final LoveErrorCode loveErrorCode;

    public LoveException(LoveErrorCode loveErrorCode) {
        super(loveErrorCode.getMessage());
        this.loveErrorCode = loveErrorCode;
    }
}
