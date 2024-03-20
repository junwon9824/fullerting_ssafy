package com.ssafy.fullerting.favorite.model.dto.response;

import com.ssafy.fullerting.favorite.model.entity.Favorite;
import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class FavoriteResponse {

    private Long id;

//    private ExArticle exArticle;
//
//    private CustomUser user;

    private boolean islike;

    private int isLikeCnt;

    public Favorite toEntity(FavoriteResponse favoriteResponse) {
        return Favorite.builder()
                .id(favoriteResponse.id)
                .build();

    }


}
