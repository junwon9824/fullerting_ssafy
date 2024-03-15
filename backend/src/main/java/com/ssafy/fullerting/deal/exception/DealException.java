package com.ssafy.fullerting.deal.exception;


import lombok.Getter;

@Getter

public class DealException extends RuntimeException {
    private final DealErrorCode exArticleErrorCode;

    public DealException (DealErrorCode dealErrorCode) {
        super(dealErrorCode.getMessage());
        this.exArticleErrorCode = dealErrorCode;
    }
}
