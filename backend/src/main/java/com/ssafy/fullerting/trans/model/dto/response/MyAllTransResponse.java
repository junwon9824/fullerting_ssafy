package com.ssafy.fullerting.trans.model.dto.response;

import com.ssafy.fullerting.exArticle.model.dto.response.ExArticleResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MyAllTransResponse {

    private Long id;

    private int price;

    private Long exarticleid;
}
