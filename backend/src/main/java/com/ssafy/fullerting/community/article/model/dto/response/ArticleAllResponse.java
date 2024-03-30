package com.ssafy.fullerting.community.article.model.dto.response;

import com.ssafy.fullerting.community.article.model.enums.ArticleType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ArticleAllResponse {
    private Long id;

    private String title;

    private String content;

    private List<String> imgurls;

    private ArticleType type;
    private int love;
    private boolean mylove;
    private Long time;
    private String authornickname;
}
