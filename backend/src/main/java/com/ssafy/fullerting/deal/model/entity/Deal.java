package com.ssafy.fullerting.deal.model.entity;

import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.user.model.entity.User;
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

    @Column(name = "deal_cur_price", nullable = false)
    private int deal_cur_price;

    public void setexarticle(ExArticle exArticle){
        this.exArticle=exArticle;
    }

}