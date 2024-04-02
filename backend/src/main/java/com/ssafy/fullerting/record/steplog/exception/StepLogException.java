package com.ssafy.fullerting.record.steplog.exception;

import lombok.Getter;

@Getter
public class StepLogException extends RuntimeException{
    private final StepLogErrorCode errorCode;

    public StepLogException(StepLogErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode=errorCode;
    }
}
