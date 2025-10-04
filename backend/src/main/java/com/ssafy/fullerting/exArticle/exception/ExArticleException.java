package com.ssafy.fullerting.exArticle.exception;

import lombok.Getter;

@Getter
public class ExArticleException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final ExArticleErrorCode exArticleErrorCode;

    public ExArticleException(ExArticleErrorCode exArticleErrorCode) {
        super(exArticleErrorCode.getMessage());
        this.exArticleErrorCode = exArticleErrorCode;
    }
}
