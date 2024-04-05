package com.ssafy.fullerting.exArticle.model.dto.request;

import com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType;
import com.ssafy.fullerting.favorite.model.entity.Favorite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ExArticleRegisterRequest {

    private Long id;
    private String exArticleTitle;
    private String exArticleContent;
    private ExArticleType exArticleType; // "제안", "일반 거래", "나눔"

    private String ex_article_location;
    private int dealCurPrice;

    @Builder.Default
    private Long packdiaryid = null; // packdiary id
    private List<Favorite> favorite;

}
