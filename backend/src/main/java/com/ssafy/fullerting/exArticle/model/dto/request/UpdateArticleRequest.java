package com.ssafy.fullerting.exArticle.model.dto.request;

import com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType;
import com.ssafy.fullerting.favorite.model.entity.Favorite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateArticleRequest {

    private String exArticleTitle;
    private String exArticleContent;
    private ExArticleType exArticleType; // "제안", "일반 거래", "나눔"

    private String ex_article_location;
    private int price;

    @Builder.Default
    private Long packdiaryid = null; // packdiary id

}
