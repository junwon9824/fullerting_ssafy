package com.ssafy.fullerting.global.config;

import lombok.Data;

@Data
public class ChatRequest {
    private Long senderId; // 전송자 ID
    private String chatRoomId; // 채팅방 ID
    private String chatMessage; // 채팅 내용
}
