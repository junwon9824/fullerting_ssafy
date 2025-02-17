package com.ssafy.fullerting.bidLog.model.entity;

import com.ssafy.fullerting.bidLog.model.dto.response.BidLogResponse;
import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.user.model.entity.MemberProfile;
import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Document(collection = "bid_log") // MongoDB의 컬렉션 이름
public class BidLog {

    @Id
    private String id; // MongoDB에서는 ID가 String으로 저장되는 경우가 많음

    @DBRef // 관계를 나타내는 어노테이션
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
                .id(Long.valueOf(this.id))
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
                .id(Long.valueOf(this.id))
                .thumbnail(user.getThumbnail())
                .nickname(user.getNickname())
                .bidcount(size)
                .build();
    }

}
