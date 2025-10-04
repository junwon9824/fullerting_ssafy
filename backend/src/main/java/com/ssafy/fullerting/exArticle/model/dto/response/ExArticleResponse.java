package com.ssafy.fullerting.exArticle.model.dto.response;

import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType;
import com.ssafy.fullerting.favorite.model.dto.response.FavoriteResponse;
import com.ssafy.fullerting.favorite.model.entity.Favorite;
import com.ssafy.fullerting.image.model.dto.response.ImageResponse;
import com.ssafy.fullerting.image.model.entity.Image;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class ExArticleResponse {

    private String exLocation;
    private Long exArticleId;
    private Long userId;  // 게시글 작성자 id
    private String exArticleTitle;
    private ExArticleType exArticleType; // "제안", "일반 거래", "나눔"
    private List<ImageResponse> imageResponses; //
    private LocalDateTime time;
    private String content;
    private boolean isdone;

}
