package com.ssafy.fullerting.favorite.model.dto.response;

import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.favorite.model.entity.Favorite;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import jakarta.persistence.*;
import lombok.Builder;

@Builder
public class FavoriteResponse {

    private Long id;

    private ExArticle exArticle;

    private CustomUser user;

    public Favorite toEntity(FavoriteResponse favoriteResponse){
        return Favorite.builder()
                .id(favoriteResponse.id)
                .exArticle(favoriteResponse.exArticle)
                .user(favoriteResponse.user)
                .build();

    };

}
