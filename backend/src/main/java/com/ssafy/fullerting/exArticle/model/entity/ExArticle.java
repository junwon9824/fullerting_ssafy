package com.ssafy.fullerting.exArticle.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.exArticle.model.dto.response.ExArticleAllResponse;
import com.ssafy.fullerting.exArticle.model.dto.response.ExArticleDetailResponse;
import com.ssafy.fullerting.exArticle.model.dto.response.ExArticleKeywordResponse;
import com.ssafy.fullerting.exArticle.model.dto.response.ExArticleResponse;
import com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType;
import com.ssafy.fullerting.favorite.model.dto.response.FavoriteResponse;
import com.ssafy.fullerting.favorite.model.entity.Favorite;
import com.ssafy.fullerting.global.BaseTimeEntity;
import com.ssafy.fullerting.image.model.entity.Image;
import com.ssafy.fullerting.record.packdiary.model.entity.PackDiary;
import com.ssafy.fullerting.trans.model.entity.Trans;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Entity
//@ToString
@Table(name = "ex_article")
@Slf4j
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

    @ManyToOne
    @JoinColumn(name = "pack_diary_id")
    private PackDiary packDiary;

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

    @OneToOne(mappedBy = "exArticle", cascade = CascadeType.ALL)
    private Deal deal;


    @OneToOne(mappedBy = "exArticle")
    private Trans trans;

    @OneToMany(mappedBy = "exArticle", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Image> image;

    @OneToMany(mappedBy = "exArticle", cascade = CascadeType.ALL)
    private List<Favorite> favorite = new ArrayList<>();

    public void setdeal(Deal deal) {
        this.deal = deal;
    }

    public void setdone() {
        this.isDone = true;
    }

    public void setpurchaserid(Long id) {
        this.purchaserId = id;
    }

    public void addfavorite(Favorite favorite) {
        this.favorite.add(favorite);
    }

    public void deletefavorite(Favorite favorite) {
        this.favorite.remove(favorite); //favorite remove
    }

    public void removeimage(Image image) {

        if (this.getImage().contains(image)) {
            this.image.remove(image);
            image.setExArticle(null);
        }

    }

    @Transactional
    public ExArticleResponse toResponse(ExArticle article, CustomUser customUser) {
        ExArticleResponse exArticleResponse = null;
//        Favorite favorite1 = null;

//        if (!article.getFavorite().isEmpty()) {
//            favorite1 = article.getFavorite().get(0);
//        }

//        log.info("typetype"+article.getType()+" "+ article.trans.getTrans_sell_price());
        Hibernate.initialize(article.getImage());

        exArticleResponse = ExArticleResponse.builder()
                .exArticleId(article.getId())
                .exArticleTitle(article.getTitle())
                .exArticleType(article.getType())
                .exLocation(article.getLocation())
//                .price(article.type.equals(ExArticleType.DEAL) ? article.deal.getDealCurPrice() : article.type.equals(ExArticleType.SHARING) ? 0 : article.trans.getTrans_sell_price())
                .imageResponses(article.getImage().stream().map(Image::toResponse)
                        .collect(Collectors.toList()))
//                .favoriteResponse(
//                        favorite1 != null ? favorite1.toResponse(customUser) : FavoriteResponse
//                                .builder().islike(false).isLikeCnt(0).build()
//                )
                .time(article.created_at)
                .isdone(article.isDone)
                .content(article.getContent())
                .userId(article.getUser().getId())
                .build();


        return exArticleResponse;
    }

    public static ExArticleKeywordResponse tokeyResponse(ExArticle article, CustomUser customUser) {
        ExArticleKeywordResponse response = null;

        //        List<FavoriteResponse> favoriteResponses
//                = article.favorite.stream().map(
//                favorite1 -> favorite1.toResponse(customUser)).collect(Collectors.toList());

        Favorite favorite1 = null;

        if (!article.getFavorite().isEmpty()) {
            favorite1 = article.getFavorite().get(0);
        }

        response = ExArticleKeywordResponse.builder()
                .exArticleResponse(article.toResponse(article, customUser))
                .favoriteResponse(favorite1 != null ? favorite1.toResponse(customUser) :
                        FavoriteResponse.builder()
                                .isLikeCnt(0)
                                .islike(false)
                                .build())
                .build();


        return response;
    }

    public static ExArticleAllResponse toAllResponse(ExArticle article, CustomUser customUser) {
        ExArticleAllResponse exArticleAllResponse = null;

        //        List<FavoriteResponse> favoriteResponses
//                = article.favorite.stream().map(
//                favorite1 -> favorite1.toResponse(customUser)).collect(Collectors.toList());

        Favorite favorite1 = null;

        if (!article.getFavorite().isEmpty()) {
            favorite1 = article.getFavorite().get(0);
        }

        exArticleAllResponse = exArticleAllResponse.builder()
//                .exArticleId(article.getId())
//                .exArticleTitle(article.getTitle())
//                .exArticleType(article.getType())
//                .exLocation(article.getLocation())
//                .imageResponses(article.getImage().stream().map(Image::toResponse)
//                        .collect(Collectors.toList()))
                .dealResponse(article.deal == null ? null : article.deal.toResponse(customUser))
                .transResponse(article.trans == null ? null : article.trans.toResponse(article.trans))

                .favoriteResponse(
                        favorite1 != null ? favorite1.toResponse(customUser) : FavoriteResponse
                                .builder().islike(false).isLikeCnt(0).build()
                )
                .exArticleResponse(
                        article.toResponse(article, customUser)
                )
                .packDiaryResponse(
                        article.packDiary != null ? article.packDiary.toResponse(article.packDiary) : null
                )
                .build();

        return exArticleAllResponse;
    }

    public static ExArticleDetailResponse toDetailResponse(ExArticle article, CustomUser customUser) {
        ExArticleDetailResponse exArticleDetailResponse = null;

        //        List<FavoriteResponse> favoriteResponses
//                = article.favorite.stream().map(
//                favorite1 -> favorite1.toResponse(customUser)).collect(Collectors.toList());

        Favorite favorite1 = null;

        if (!article.getFavorite().isEmpty()) {
            favorite1 = article.getFavorite().get(0);
        }


        exArticleDetailResponse = exArticleDetailResponse.builder()
//                .exArticleId(article.getId())
//                .exArticleTitle(article.getTitle())
//                .exArticleType(article.getType())
//                .exLocation(article.getLocation())
                .imageResponses(article.getImage().stream().map(Image::toResponse)
                        .collect(Collectors.toList()))
                .favoriteResponse(
                        favorite1 != null ? favorite1.toResponse(customUser) : FavoriteResponse
                                .builder().islike(false).isLikeCnt(0).build()
                )
                .userResponse(customUser.toResponse())
                .dealResponse(article.deal != null ? article.deal.toResponse(customUser) : null)
                .packDiaryResponse(article.packDiary != null ?
                        article.packDiary.toResponse(article.getPackDiary())
                        : null
                )
                .transResponse(
                        article.trans != null ?
                                article.trans.toResponse(article.trans)
                                : null
                )
                .exArticleResponse(article.toResponse(article, customUser))
                .build();


        return exArticleDetailResponse;
    }

    public void addimage(Image image) {
        this.image.add(image);
        image.setExArticle(this);
    }
}
