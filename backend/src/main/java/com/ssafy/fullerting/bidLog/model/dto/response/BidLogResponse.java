package com.ssafy.fullerting.bidLog.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BidLogResponse(
        Long id,
        String nickname,
        String thumbnail,
        Long userId,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime localDateTime,
        int bidLogPrice,
        Long exarticleid,
        int bidcount) {
    // Record automatically provides:
    // 1. All-args constructor
    // 2. Getters (field names as method names)
    // 3. equals(), hashCode(), toString()
    // 4. Immutable fields (final by default)
}
