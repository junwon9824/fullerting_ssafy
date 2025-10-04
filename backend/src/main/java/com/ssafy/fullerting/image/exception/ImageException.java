package com.ssafy.fullerting.image.exception;


import lombok.Getter;

@Getter

public class ImageException extends RuntimeException {
    private final ImageErrorCode  imageErrorCode;

    public ImageException(ImageErrorCode exArticleErrorCode) {
        super(exArticleErrorCode.getMessage());
        this.imageErrorCode = exArticleErrorCode;
    }
}
