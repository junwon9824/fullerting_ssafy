package com.ssafy.fullerting.exArticle.model.dto.response;

import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType;
import com.ssafy.fullerting.favorite.model.dto.response.FavoriteResponse;
import com.ssafy.fullerting.favorite.model.entity.Favorite;
import com.ssafy.fullerting.image.model.dto.response.ImageResponse;
import com.ssafy.fullerting.image.model.entity.Image;
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
    private ExArticleType exArticleType; // "제안", "일반 거래", "나눔"
    private List<ImageResponse> imageResponses; //
    private List<FavoriteResponse> favoriteResponse;

    public ExArticle toEntity(ExArticleResponse exArticleResponse) {
        return ExArticle.builder()
                .location(exArticleResponse.exLocation)
                .title(exArticleResponse.exArticleTitle)
                .type(exArticleResponse.exArticleType)
                .favorite(favoriteResponse.stream()
                        .map(favoriteResponse1 -> favoriteResponse1.toEntity(favoriteResponse1))
                        .collect(Collectors.toList()))
                .build();
    }

    public static ExArticleResponse fromEntity(ExArticle article) {
        return ExArticleResponse.builder()
                .exLocation(article.getLocation())
                .exArticleId(article.getId())
                .exArticleTitle(article.getTitle())
                .exArticleType(article.getType())
                .imageResponses(article.getImage().stream()
                        .map(Image::toResponse)
                        .collect(Collectors.toList()))
                .favoriteResponse(article.getFavorite().stream()
                        .map(Favorite::toResponse)
                        .collect(Collectors.toList()))
                .build();

    }

}
