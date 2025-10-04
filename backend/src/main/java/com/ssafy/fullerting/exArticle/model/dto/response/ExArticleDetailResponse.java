package com.ssafy.fullerting.exArticle.model.dto.response;

import com.ssafy.fullerting.deal.model.dto.response.DealResponse;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType;
import com.ssafy.fullerting.favorite.model.dto.response.FavoriteResponse;
import com.ssafy.fullerting.favorite.model.entity.Favorite;
import com.ssafy.fullerting.image.model.dto.response.ImageResponse;
import com.ssafy.fullerting.image.model.entity.Image;
import com.ssafy.fullerting.record.packdiary.model.dto.response.PackDiaryResponse;
import com.ssafy.fullerting.trans.model.dto.response.TransResponse;
import com.ssafy.fullerting.user.model.dto.response.UserResponse;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class ExArticleDetailResponse {

    private List<ImageResponse> imageResponses; //
    private FavoriteResponse favoriteResponse;
    private PackDiaryResponse packDiaryResponse;
    private ExArticleResponse exArticleResponse;
    private DealResponse dealResponse;
    private UserResponse userResponse;
    private TransResponse transResponse;


    

//    public ExArticle toEntity(ExArticleResponse exArticleResponse) {
//        return ExArticle.builder()
//                .location(exArticleResponse.exLocation)
//                .title(exArticleResponse.exArticleTitle)
//                .type(exArticleResponse.exArticleType)
//                .favorite(favoriteResponse.toEntity(favoriteResponse))
//                .build();
//    }

//    public static ExArticleResponse fromEntity(ExArticle article) {
//        return ExArticleResponse.builder()
//                .exLocation(article.getLocation())
//                .exArticleId(article.getId())
//                .exArticleTitle(article.getTitle())
//                .exArticleType(article.getType())
//                .imageResponses(article.getImage().stream()
//                        .map(Image::toResponse)
//                        .collect(Collectors.toList()))
//
//                .favoriteResponse(article.getFavorite().stream()
//                        .map(Favorite::toResponse)
//                        .collect(Collectors.toList()))
//
//                .build();
//
//    }

}
