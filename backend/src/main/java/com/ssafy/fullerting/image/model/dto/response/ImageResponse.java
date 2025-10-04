package com.ssafy.fullerting.image.model.dto.response;

import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.image.model.entity.Image;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString

public class ImageResponse {

    private Long id;

//    private ExArticle exArticle;

    private String imgStoreUrl;

    public Image toEntity(ImageResponse imageResponse){
        return Image.builder()
                .id(imageResponse.getId())
                .imgStoreUrl(imageResponse.getImgStoreUrl())
//                .exArticle(imageResponse.getExArticle())
                .build();
    }

}
