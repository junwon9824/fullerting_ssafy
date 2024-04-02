package com.ssafy.fullerting.deal.model.dto.response;

import com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType;
import com.ssafy.fullerting.image.model.dto.response.ImageResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class MyExArticleResponse {

    private String exLocation;
    private Long exArticleId;
    private Long userId;  // 게시글 작성자 id
    private String exArticleTitle;
    private ExArticleType exArticleType; // "제안", "일반 거래", "나눔"
    private List<ImageResponse> imageResponses; //
    //    private int price;
    private LocalDateTime time;
    private String content;
    private boolean isdone;
    private int price;

}
