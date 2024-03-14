package com.ssafy.fullerting.exArticle.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ExArticleRegisterRequest {


    private String exArticleTitle;
    private String exArticleContent;
    private String exArticlePlace;
    private Enum exArticlePayment; // "제안", "일반 거래", "나눔"
    private List<String> img; // 파일 업로드를 위한 리스트

}
