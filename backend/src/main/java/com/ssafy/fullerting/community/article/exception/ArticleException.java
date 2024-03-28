package com.ssafy.fullerting.community.article.exception;


import lombok.Getter;

@Getter

public class ArticleException extends RuntimeException {

    private final ArticleErrorCode articleErrorCode;

    public ArticleException(ArticleErrorCode articleErrorCode) {
        super(articleErrorCode.getMessage());
        this.articleErrorCode = articleErrorCode;
    }
}
