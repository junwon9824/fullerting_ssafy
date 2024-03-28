package com.ssafy.fullerting.community.love.model.entity;

import com.ssafy.fullerting.community.article.model.entity.Article;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@ToString
@Table(name = "love")

public class Love {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "love_id", nullable = false)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private CustomUser customUser;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;


}
