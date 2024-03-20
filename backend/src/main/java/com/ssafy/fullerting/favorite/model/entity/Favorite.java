package com.ssafy.fullerting.favorite.model.entity;

import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.favorite.model.dto.response.FavoriteResponse;
import com.ssafy.fullerting.user.model.dto.response.UserResponse;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

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

    public FavoriteResponse toResponse(CustomUser user) { // 이거 수정 ..
        // 해당 게시글에 얼마나 많은 유저가 좋아요 눌럿나, 내가 좋아요 누른지 여부

        return FavoriteResponse.builder()
                .id(this.id)
                .isLikeCnt(this.exArticle.getFavorite().size())
                .islike(this.user == user ? true : false)
                .build();
    }

}
