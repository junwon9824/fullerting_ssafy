package com.ssafy.fullerting.deal.model.dto.response;

import com.ssafy.fullerting.exArticle.model.dto.response.ExArticleResponse;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DealResponse {

    private Long id;

    private int price;
//    private ExArticleResponse exArticleResponse;
    
}
