package com.ssafy.fullerting.bidLog.model.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ssafy.fullerting.bidLog.model.dto.response.BidLogResponse;
import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.user.model.entity.MemberProfile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "bid_logs")
@CompoundIndexes({
        @CompoundIndex(name = "unique_bid", def = "{'deal': 1, 'userId': 1, 'bidLogPrice': 1}", unique = true)
})
public class BidLog {
    @Id
    private Long id;

    @DBRef
    private Deal deal;

    private Long userId;
    private LocalDateTime localDateTime;
    private int bidLogPrice;

    // 모든 필드를 포함하는 변환 메서드
    public BidLogResponse toBidLogResponse(MemberProfile customUser, Long exarticleid, Integer bidcount) {
        return BidLogResponse.builder()
                .id(this.id)
                .bidLogPrice(this.bidLogPrice)
                .userId(this.userId)
                .localDateTime(this.localDateTime)
                .nickname(customUser != null ? customUser.getNickname() : null)
                .thumbnail(customUser != null ? customUser.getThumbnail() : null)
                .exarticleid(exarticleid)
                .bidcount(bidcount)
                .build();
    }
    public static final String SEQUENCE_NAME = "bid_log_sequence";

}
