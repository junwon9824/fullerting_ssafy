package com.ssafy.fullerting.global.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidRequestMessage {
    private Long exArticleId;
    private int dealCurPrice;
    private String bidderUserName;
}
