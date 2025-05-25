package com.ssafy.fullerting.global.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class BidNotification {

    @JsonProperty("userid")
    private long userId;

    @JsonProperty("articleid")
    private long articleId;

    private String redirectUrl;
    private int price;
    private LocalDateTime localDateTime;

    // @JsonCreator와 @JsonProperty를 사용하여 객체를 생성
    @JsonCreator
    public BidNotification(
            @JsonProperty("userid") long userId,
            @JsonProperty("articleid") long articleId,
            @JsonProperty("redirectUrl") String redirectUrl,
            @JsonProperty("price") int price,
            @JsonProperty("localDateTime") LocalDateTime localDateTime) {
        this.userId = userId;
        this.articleId = articleId;
        this.redirectUrl = redirectUrl;
        this.price = price;
        this.localDateTime = localDateTime;
    }
}
