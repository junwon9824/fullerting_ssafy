package com.ssafy.fullerting.bidLog.model.entity;

import com.ssafy.fullerting.bidLog.model.dto.response.BidLogResponse;
import com.ssafy.fullerting.user.model.entity.MemberProfile;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "bid_logs")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BidLog {

    @Id
    private String id;  // MongoDB ID

    private Long dealId;  // MongoDB에서는 외래 키로 dealId만 저장
    private Long userId;  // 입찰자 아이디
    private LocalDateTime localDateTime;
    private int bidLogPrice;

    public BidLogResponse toBidLogResponse(BidLog BidLog, MemberProfile customUser) {
        return BidLogResponse.builder()
                .bidLogPrice(BidLog.getBidLogPrice())
                .userId(BidLog.getUserId())
                .localDateTime(BidLog.getLocalDateTime())
                .exarticleid(BidLog.getDealId())  // dealId만 사용
                .id(Long.valueOf(BidLog.getId()))  // MongoDB ID
                .nickname(customUser.getNickname())
                .thumbnail(customUser.getThumbnail())
                .build();
    }

    public BidLogResponse toBidLogSuggestionResponse(BidLog BidLog, MemberProfile user, int size) {
        return BidLogResponse.builder()
                .bidLogPrice(BidLog.getBidLogPrice())
                .userId(BidLog.getUserId())
                .localDateTime(BidLog.getLocalDateTime())
                .exarticleid(BidLog.getDealId())  // dealId
                .id(Long.valueOf(BidLog.getId()))  // MongoDB ID
                .thumbnail(user.getThumbnail())
                .nickname(user.getNickname())
                .bidcount(size)
                .build();
    }
}
