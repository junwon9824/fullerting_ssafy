package com.ssafy.fullerting.global.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.user.model.entity.MemberProfile;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BidNotification {


    @JsonProperty("userid") // JSON에서의 키와 매핑
    private long userId;

    @JsonProperty("articleid") // JSON에서의 키와 매핑
    private long articleId;
    private String redirectUrl;



    // Getters and Setters
}