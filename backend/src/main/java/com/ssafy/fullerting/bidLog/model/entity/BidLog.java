package com.ssafy.fullerting.bidLog.model.entity;

import com.ssafy.fullerting.bidLog.model.dto.response.BidLogResponse;
import com.ssafy.fullerting.deal.model.entity.Deal;
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
    private Long userId;

    @Column(name = "bid_log_time", nullable = false)
    private LocalDateTime localDateTime;


    @Column(name = "bid_log_price", nullable = false)
    private int bidLogPrice;

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    public BidLogResponse tobidLogResponse(BidLog bidLog,CustomUser customUser) {

        return BidLogResponse.builder()
                .bidLogPrice(bidLog.bidLogPrice)
                .userId(bidLog.userId)
                .localDateTime(bidLog.localDateTime)
                .exarticleid(bidLog.deal.getExArticle().getId())
                .id(bidLog.id)
                .nickname(customUser.getNickname() )
                .thumbnail(customUser.getThumbnail())
                .build();
    }

    public BidLogResponse toBidLogsuggestionResponse(BidLog bidLog,CustomUser user   ) {

        return BidLogResponse.builder()
                .bidLogPrice(bidLog.bidLogPrice)
                .userId(bidLog.userId)
                .localDateTime(bidLog.localDateTime)
                .exarticleid(bidLog.deal.getExArticle().getId())
                .id(bidLog.id)
                .thumbnail(user.getThumbnail())
                .nickname(user.getNickname())
                .build();
    }

}
