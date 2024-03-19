package com.ssafy.fullerting.favorite.model.entity;

import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.favorite.model.dto.response.FavoriteResponse;
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

@Table(name = "favorite")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "ex_article_id")
    private ExArticle exArticle;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CustomUser user;

    public FavoriteResponse toResponse(){
        return FavoriteResponse.builder()
                .id(this.id)
                .exArticle(this.exArticle)
                .user(this.user)
                .build();
    }

}
