package com.ssafy.fullerting.exArticle.model.dto.response;

import com.ssafy.fullerting.deal.model.dto.response.DealResponse;
import com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType;
import com.ssafy.fullerting.favorite.model.dto.response.FavoriteResponse;
import com.ssafy.fullerting.image.model.dto.response.ImageResponse;
import com.ssafy.fullerting.record.packdiary.model.dto.response.PackDiaryResponse;
import com.ssafy.fullerting.trans.model.dto.response.TransResponse;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class ExArticleAllResponse {
    private ExArticleResponse exArticleResponse;
    private PackDiaryResponse packDiaryResponse;
    private FavoriteResponse favoriteResponse;
    private DealResponse dealResponse;
    private TransResponse transResponse;


}
