package com.ssafy.fullerting.community.article.model.entity;

import com.ssafy.fullerting.community.article.model.enums.ArticleType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


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
    private ArticleType type;

//    @OneToMany(mappedBy = "")
//    private Love love;
}
