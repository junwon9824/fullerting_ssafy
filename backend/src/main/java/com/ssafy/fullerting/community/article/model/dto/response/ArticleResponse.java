package com.ssafy.fullerting.community.article.model.dto.response;

import com.ssafy.fullerting.community.article.model.enums.ArticleType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleResponse {


    private String title;

    private String content;


    private ArticleType type;

}
