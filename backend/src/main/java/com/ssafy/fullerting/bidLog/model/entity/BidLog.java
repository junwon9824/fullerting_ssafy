package com.ssafy.fullerting.bidLog.model.entity;

import com.ssafy.fullerting.bidLog.model.dto.response.BidLogResponse;
import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.user.model.entity.MemberProfile;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Document(collection = "bid_logs") // MongoDB collection name
public class BidLog {
    @Id
    private Long id; // MongoDB uses Long for _id

    @DBRef // Reference to Deal document
    private Deal deal;

    private Long userId; // 입찰자 아이디
    private LocalDateTime localDateTime;
    private int bidLogPrice;

    public BidLogResponse toBidLogResponse(MemberProfile customUser) {
        return BidLogResponse.builder()
                .bidLogPrice(this.bidLogPrice)
                .userId(this.userId)
                .localDateTime(this.localDateTime)
                .exarticleid(this.deal.getExArticle().getId())
                .id(this.id)
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
                .id(this.id)
                .thumbnail(user.getThumbnail())
                .nickname(user.getNickname())
                .bidcount(size)
                .build();
    }
}