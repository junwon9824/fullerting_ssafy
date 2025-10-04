package com.ssafy.fullerting.trans.exception;


import lombok.Getter;

@Getter

public class TransException extends RuntimeException {

    private final TransErrorCode transErrorCode;

    public TransException(TransErrorCode transErrorCode) {

        super(transErrorCode.getMessage());
        this.transErrorCode = transErrorCode;
    }
}
