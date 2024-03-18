package com.ssafy.fullerting.bid_log.model.entity;

import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
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
@Table(name = "bid_log")
public class BidLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid_log_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "deal_id")
    private Deal deal;

    @Column(name = "user_id", nullable = false)
    private Long user_id;

    @Column(name = "bid_log_time", nullable = false)
    private LocalDateTime localDateTime;


    @Column(name = "bid_log_price", nullable = false)
    private int bid_log_price;

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

}
