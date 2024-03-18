package com.ssafy.fullerting.exArticle.model.dto.request;

import com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ExArticleRegisterRequest {


    private String exArticleTitle;
    private String exArticleContent;
    private String exArticlePlace;
    private ExArticleType ExArticleType; // "제안", "일반 거래", "나눔"
    private List<String> img; // 파일 업로드를 위한 리스트
    private String ex_article_location;
    private int deal_cur_price;

}
