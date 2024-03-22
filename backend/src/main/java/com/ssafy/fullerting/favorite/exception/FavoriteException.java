package com.ssafy.fullerting.favorite.exception;


import lombok.Getter;

@Getter
public class FavoriteException extends RuntimeException {
    private final FavoriteErrorCode likeErrorCode;

    public FavoriteException(FavoriteErrorCode likeErrorCode ) {
        super(likeErrorCode.getMessage());
        this.likeErrorCode= likeErrorCode;
    }
}
