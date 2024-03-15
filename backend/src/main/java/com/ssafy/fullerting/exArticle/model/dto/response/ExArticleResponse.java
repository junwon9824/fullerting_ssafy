package com.ssafy.fullerting.exArticle.model.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ExArticleResponse {

    private Long id;
    private String exLocation;
    private int exArticleId;
    private String exArticleTitle;
    private Enum exArticlePayment; // "제안", "일반 거래", "나눔"
    private String img; //
}
