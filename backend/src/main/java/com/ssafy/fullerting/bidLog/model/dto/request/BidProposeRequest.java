package com.ssafy.fullerting.bidLog.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BidProposeRequest {
    private int dealCurPrice;
    private Long userId; // Added field for Kafka consumer
}
