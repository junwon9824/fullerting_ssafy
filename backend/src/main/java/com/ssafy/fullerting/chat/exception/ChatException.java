package com.ssafy.fullerting.chat.exception;

import lombok.Getter;

@Getter
public class ChatException extends RuntimeException{
    private final ChatErrorCode errorCode;

    public ChatException(ChatErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode=errorCode;
    }
}
