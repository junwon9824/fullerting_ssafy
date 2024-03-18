package com.ssafy.fullerting.exArticle.model.entity;

import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.exArticle.model.dto.response.ExArticleResponse;
import com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType;
import com.ssafy.fullerting.favorite.model.entity.Favorite;
import com.ssafy.fullerting.global.BaseTimeEntity;
import com.ssafy.fullerting.image.model.entity.Image;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@ToString
@Table(name = "ex_article")
public class ExArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ex_article_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CustomUser user;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_at;

//    @ManyToOne
//    @JoinColumn(name = "pack_diary_id")
//    private PackDiary packDiary;

    @Column(name = "ex_article_title", nullable = false)
    private String title;

    @Column(name = "ex_article_content", nullable = false, length = 2000)
    private String content;

    @Column(name = "ex_article_location", nullable = false)
    private String location;

    @Column(name = "ex_article_place", length = 100)
    private String place;

    @Enumerated(EnumType.STRING)
    @Column(name = "ex_article_type", nullable = false)
    private ExArticleType type;

    @Column(name = "ex_article_is_done", nullable = false)
    private boolean isDone;

    @Column(name = "ex_article_purchaser_id")
    private Long purchaserId;

    @OneToOne(mappedBy = "exArticle")
    private Deal deal;


    @OneToMany(mappedBy = "exArticle")
    private List<Image> image;

    @OneToMany(mappedBy = "exArticle")
    private List<Favorite> favorite;

    public void setdeal(Deal deal) {
        this.deal = deal;
    }

    public void setfavorite(List<Favorite> favorite) {
        this.favorite = favorite;
    }

    public static ExArticleResponse fromEntity(ExArticle article) {
        return ExArticleResponse.builder()
                .exArticleId(article.id)
                .exArticleTitle(article.title)
                .ExArticleType(article.type)
                .exLocation(article.location)
                .img(article.image.stream().
                        map(Image::getImg_store_url)
                        .collect(Collectors.toList()))
                .favoriteResponse(article.favorite.stream()
                        .map(Favorite::toResponse)
                        .collect(Collectors.toList()))
                .build();
    }


}
