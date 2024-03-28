package com.ssafy.fullerting.community.article.model.entity;

import com.ssafy.fullerting.community.article.model.dto.response.ArticleResponse;
import com.ssafy.fullerting.community.article.model.enums.ArticleType;
import com.ssafy.fullerting.community.love.model.entity.Love;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@ToString
@Table(name = "article")

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


    public void addlove(Love love) {
        loves.add(love);
    }

    public void removelove(Love love) {
        loves.remove(love);
    }

    public ArticleResponse toResponse() {
        return ArticleResponse.builder()
                .title(this.title)
                .id(this.id)
                .content(this.content)
                .type(this.type)
                .love(this.getLoves().size())
                .build();
    }

//    @OneToMany(mappedBy = "")
//    private Love love;
}
