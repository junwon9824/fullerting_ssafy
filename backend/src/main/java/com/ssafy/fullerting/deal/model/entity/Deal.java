package com.ssafy.fullerting.deal.model.entity;

import com.ssafy.fullerting.bidLog.model.entity.BidLog;
import com.ssafy.fullerting.deal.model.dto.response.DealResponse;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
    private int dealCurPrice;

    @OneToMany(mappedBy = "deal",cascade = CascadeType.ALL)
    private List<BidLog> bidLog;

    public void setexarticle(ExArticle exArticle) {
        this.exArticle = exArticle;
    }

    public DealResponse toResponse(CustomUser customUser) {
        return DealResponse.builder()
//                .exArticleResponse(this.exArticle.toResponse(this.exArticle,customUser))
                .price(this.getDealCurPrice()).id(this.getId()).
//   .price(article.type.equals(ExArticleType.DEAL) ? article.deal.getDealCurPrice() : article.type.equals(ExArticleType.SHARING) ? 0 : article.trans.getTrans_sell_price())

        build();
    }
}
