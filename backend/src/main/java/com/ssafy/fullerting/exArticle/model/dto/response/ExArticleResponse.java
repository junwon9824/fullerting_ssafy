package com.ssafy.fullerting.exArticle.model.dto.response;

import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType;
import com.ssafy.fullerting.favorite.model.dto.response.FavoriteResponse;
 import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ExArticleResponse {

    private String exLocation;
    private Long exArticleId;
    private String exArticleTitle;
    private ExArticleType ExArticleType; // "제안", "일반 거래", "나눔"
    private List<String> img; //
    private List<FavoriteResponse> favoriteResponse;

    public ExArticle toEntity(ExArticleResponse exArticleResponse) {
        return ExArticle.builder()
                .location(exArticleResponse.exLocation)
                .title(exArticleResponse.exArticleTitle)
                .type(exArticleResponse.ExArticleType)
                .favorite(favoriteResponse.stream()
                        .map(favoriteResponse1 -> favoriteResponse1.toEntity(favoriteResponse1))
                        .collect(Collectors.toList()))
                .build();
    }

}
