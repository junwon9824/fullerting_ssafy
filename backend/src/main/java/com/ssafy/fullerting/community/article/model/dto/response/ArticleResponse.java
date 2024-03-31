package com.ssafy.fullerting.community.article.model.dto.response;

import com.ssafy.fullerting.community.article.model.enums.ArticleType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
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
    private String authornickname;
    private String rank;
    private String thumbnail;
    private LocalDateTime createdAt;
    private int commentsize;



//    닉네임,등급,섬네일 불러오기
}
