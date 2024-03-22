package com.ssafy.fullerting.image.model.entity;

import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType;
import com.ssafy.fullerting.image.model.dto.response.ImageResponse;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
//@ToString
@Table(name = "img_store")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "img_store_id")
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "article_id")
//    private Article article;
//
//
//    @ManyToOne
//    @JoinColumn(name = "article_id")
//    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ex_article_id")
    private ExArticle exArticle;

    private String img_store_url;

    public static ImageResponse toResponse(Image image)
    {
        return ImageResponse.builder()
                .img_store_url(image.getImg_store_url())
                .id(image.getId())
                .build();
    }


}
