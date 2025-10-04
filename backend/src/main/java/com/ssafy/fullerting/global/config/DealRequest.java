package com.ssafy.fullerting.global.config;

import lombok.Data;

@Data
public class DealRequest {
    private Long senderId; // 전송자 ID
    private String dealRoomId; // 채팅방 ID
    private String dealMessage; // 채팅 내용
}
