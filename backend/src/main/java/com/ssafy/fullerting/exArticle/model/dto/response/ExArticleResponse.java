package com.ssafy.fullerting.exArticle.model.dto.response;

import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ExArticleResponse {

    private Long id;
    private String exLocation;
    private Long exArticleId;
    private String exArticleTitle;
    private ExArticleType ExArticleType; // "제안", "일반 거래", "나눔"
    private List<String> img; //

    public ExArticle toEntity(ExArticleResponse exArticleResponse){
        return ExArticle.builder()
                .location(exArticleResponse.exLocation)
                .id(exArticleResponse.id)
                .title(exArticleResponse.exArticleTitle)
                .type(exArticleResponse.ExArticleType)

                .build();
    }
}
