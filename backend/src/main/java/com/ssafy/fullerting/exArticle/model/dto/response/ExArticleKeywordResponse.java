package com.ssafy.fullerting.exArticle.model.dto.response;

import com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType;
import com.ssafy.fullerting.favorite.model.dto.response.FavoriteResponse;
import com.ssafy.fullerting.image.model.dto.response.ImageResponse;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class ExArticleKeywordResponse {

    private ExArticleResponse exArticleResponse;
    private FavoriteResponse favoriteResponse;

}
