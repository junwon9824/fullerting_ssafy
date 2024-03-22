package com.ssafy.fullerting.trans.model.entity;

import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.trans.model.dto.response.MyAllTransResponse;
import com.ssafy.fullerting.trans.model.dto.response.TransResponse;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
//@ToString
@Table(name = "trans")
@Slf4j
public class Trans {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trans_id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "ex_article_id", nullable = false)
    private ExArticle exArticle;

    @Column(name = "trans_sell_price", nullable = false)
    private int trans_sell_price;

    public void setexarticle(ExArticle exArticle) {
        this.exArticle = exArticle;
    }

    public TransResponse toResponse(Trans trans) {
        return TransResponse.builder()
                .id(trans.getId())
                .price(trans.getTrans_sell_price())
                .build();
    }

    public MyAllTransResponse toMyAllTransResponse(Trans trans, CustomUser customUser) {
//        log.info("aaaaaaaaaaaaa"+trans.getExArticle());

        return MyAllTransResponse.builder()
                .id(trans.getId())
                .exarticleid(trans.getExArticle().getId())
                .build();
    }

}
