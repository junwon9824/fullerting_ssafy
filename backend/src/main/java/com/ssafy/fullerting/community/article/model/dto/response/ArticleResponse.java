package com.ssafy.fullerting.community.article.model.dto.response;

import com.ssafy.fullerting.community.article.model.enums.ArticleType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ArticleResponse {

    private Long id;

    private String title;

    private String content;


    private ArticleType type;
    private int love;
    private boolean mylove;
    private List<String> imgurls;
}
