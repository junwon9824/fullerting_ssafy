package com.ssafy.fullerting.record.packdiary.exception;

import lombok.Getter;

@Getter
public class PackDiaryException extends RuntimeException{
    private final PackDiaryErrorCode errorCode;

    public PackDiaryException(PackDiaryErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode=errorCode;
    }
}
