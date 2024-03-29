package com.ssafy.fullerting.image.model.entity;

import com.ssafy.fullerting.community.article.model.entity.Article;
import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType;
import com.ssafy.fullerting.image.model.dto.response.ImageResponse;
import com.ssafy.fullerting.record.diary.model.entity.Diary;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ex_article_id")
    private ExArticle exArticle;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;


    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;


    private String imgStoreUrl;

    public static ImageResponse toResponse(Image image)
    {
        return ImageResponse.builder()
                .imgStoreUrl(image.getImgStoreUrl())
                .id(image.getId())
                .build();
    }


}
