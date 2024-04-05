package com.ssafy.fullerting.crop.step.exception;

import lombok.Getter;

@Getter
public class CropStepException extends RuntimeException{
    private final CropStepErrorCode errorCode;

    public CropStepException(CropStepErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode=errorCode;
    }
}
