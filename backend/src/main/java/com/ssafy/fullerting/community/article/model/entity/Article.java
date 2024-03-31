package com.ssafy.fullerting.community.article.model.entity;

import com.ssafy.fullerting.community.article.model.dto.response.ArticleAllResponse;
import com.ssafy.fullerting.community.article.model.dto.response.ArticleResponse;
import com.ssafy.fullerting.community.article.model.enums.ArticleType;
import com.ssafy.fullerting.community.comment.model.entity.Comment;
import com.ssafy.fullerting.community.love.model.entity.Love;
import com.ssafy.fullerting.image.model.entity.Image;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.hibernate.Hibernate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@ToString
@Table(name = "article")
@Slf4j
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id", nullable = false)
    private Long id;


    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "article_title", nullable = false, length = 30)
    private String title;

    @Column(name = "article_content", nullable = false, length = 5000)
    private String content;

    @Column(name = "article_created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "article_love", nullable = false)
    private int love;

    @Column(name = "article_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ArticleType type;

    @OneToMany(mappedBy = "article")
    private List<Love> loves = new ArrayList<>();


    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();


    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();


    public void addlove(Love love) {
        loves.add(love);
    }
//
//    public void addcomment(Comment comment) {
//        comments.add(comment);
//    }
//
//    public void removecomment(Comment comment) {
//        comments.remove(comment);
//    }


    //
//    public void addimage(Image image) {
//        if (this.images == null) {
//            this.images = new ArrayList<>();
//        }
//        this.images.add(image);
//
//    }
//
    public void removeimage(Image image) {
        this.images.remove(image);
        log.info("removeimage"+this.images.size());
    }

    public void removelove(Love love) {
        loves.remove(love);
    }

    public ArticleResponse toResponse(Article article, boolean mylove, CustomUser customUser) {
        Hibernate.initialize(article.getImages());
        LocalDateTime currentTime = LocalDateTime.now();
        Duration timeDifference = Duration.between(article.getCreatedAt(), currentTime);
        long minutesDifference = timeDifference.toMinutes(); // 분으로 환산


        return ArticleResponse.builder()
                .title(article.getTitle())
                .id(article.getId())
                .content(article.getContent())
                .type(article.getType())
                .love(article.getLove())
                .mylove(mylove)
                .authornickname(customUser.getNickname())
                .rank(customUser.getRank())
                .thumbnail(customUser.getThumbnail())
                .time(minutesDifference)
                .commentsize(article.getComments().size())
                .imgurls(article.getImages() != null ? article.getImages().stream().map(Image::getImgStoreUrl).collect(Collectors.toList()) : null)
                .build();
    }

    public ArticleAllResponse toAllResponse(Article article, boolean mylove, String authornickname) {

        Hibernate.initialize(article.getImages());
        LocalDateTime currentTime = LocalDateTime.now();
        Duration timeDifference = Duration.between(article.getCreatedAt(), currentTime);
        long minutesDifference = timeDifference.toMinutes(); // 분으로 환산


        return ArticleAllResponse.builder()
                .title(article.getTitle())
                .id(article.getId())
                .content(article.getContent())
                .type(article.getType())
                .love(article.getLove())
                .imgurls(article.getImages() != null ? article.getImages().stream().map(Image::getImgStoreUrl).collect(Collectors.toList()) : null)
                .mylove(mylove)
                .time(minutesDifference)
                .authornickname(authornickname)
                .commentsize(article.getComments().size())
                .build();
    }


//    @OneToMany(mappedBy = "")
//    private Love love;
}
