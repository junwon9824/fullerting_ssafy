package com.ssafy.fullerting.bidLog.exception;


import lombok.Getter;

@Getter

public class BidException extends RuntimeException {

    private  final BidErrorCode bidErrorCode;
    public BidException(BidErrorCode bidErrorCode) {
        super(bidErrorCode.getMessage());
        this.bidErrorCode = bidErrorCode;
    }
}
