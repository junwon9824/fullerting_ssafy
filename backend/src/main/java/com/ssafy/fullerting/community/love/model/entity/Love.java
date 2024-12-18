package com.ssafy.fullerting.community.love.model.entity;

import com.ssafy.fullerting.community.article.model.entity.Article;
import com.ssafy.fullerting.user.model.entity.MemberProfile;
import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "love")

public class Love {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "love_id", nullable = false)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private MemberProfile customUser;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;


}
