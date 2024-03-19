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

    private ExArticle exArticle;

    private String img_store_url;

    public Image toEntity(Image image){
        return Image.builder()
                .id(image.getId())
                .img_store_url(image.getImg_store_url())
                .exArticle(image.getExArticle())

                .build();
    }

}
