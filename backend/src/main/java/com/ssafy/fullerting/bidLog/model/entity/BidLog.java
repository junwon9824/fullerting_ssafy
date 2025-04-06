package com.ssafy.fullerting.bidLog.model.entity;

import com.ssafy.fullerting.bidLog.model.dto.response.BidLogResponse;
import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.user.model.entity.MemberProfile;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "bid_log") // MySQL의 테이블 이름
public class BidLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // MySQL에서는 ID가 Long으로 저장됨

    @ManyToOne // Deal과의 관계를 나타내는 어노테이션
    @JoinColumn(name = "deal_id", nullable = false) // 외래 키 컬럼 이름
    private Deal deal;

    private Long userId; // 입찰자 아이디

    private LocalDateTime localDateTime;

    private int bidLogPrice;

    public BidLogResponse toBidLogResponse(BidLog bidLog, MemberProfile customUser) {
        return BidLogResponse.builder()
                .bidLogPrice(this.bidLogPrice)
                .userId(this.userId)
                .localDateTime(this.localDateTime)
                .exarticleid(this.deal.getExArticle().getId())
                .id(this.id) // Long 타입으로 변경
                .nickname(customUser.getNickname())
                .thumbnail(customUser.getThumbnail())
                .build();
    }

    public BidLogResponse toBidLogSuggestionResponse(BidLog bidLog1, MemberProfile user, int size) {
        return BidLogResponse.builder()
                .bidLogPrice(this.bidLogPrice)
                .userId(this.userId)
                .localDateTime(this.localDateTime)
                .exarticleid(this.deal.getExArticle().getId())
                .id(this.id) // Long 타입으로 변경
                .thumbnail(user.getThumbnail())
                .nickname(user.getNickname())
                .bidcount(size)
                .build();
    }

}
