package com.ssafy.fullerting.favorite.exception;


import lombok.Getter;

@Getter
public class likeException extends RuntimeException {
    private final LikeErrorCode  likeErrorCode;

    public likeException(LikeErrorCode likeErrorCode ) {
        super(likeErrorCode.getMessage());
        this.likeErrorCode= likeErrorCode;
    }
}
