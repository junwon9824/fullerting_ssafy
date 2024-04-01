package com.ssafy.fullerting.community.article.model.dto.request;

import com.ssafy.fullerting.community.article.model.enums.ArticleType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UpdateArticleRequest {
    private List<Long> images = new ArrayList<>();


    private String title;

    private String content;

    private ArticleType type;
}
