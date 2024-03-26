package com.ssafy.fullerting.deal.model.entity;

import com.ssafy.fullerting.deal.model.dto.response.DealResponse;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.user.model.entity.CustomUser;
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
@Table(name = "deal")
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deal_id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "ex_article_id", nullable = false)
    private ExArticle exArticle;

    @Column(name = "dealCurPrice", nullable = false)
    private int dealCurPrice;

    public void setexarticle(ExArticle exArticle){
        this.exArticle=exArticle;
    }

    public DealResponse toResponse(CustomUser customUser){
        return DealResponse.builder()
                .exArticleResponse(this.exArticle.toResponse(this.exArticle,customUser))
                .build();
    }
}
