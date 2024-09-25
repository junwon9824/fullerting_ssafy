package com.ssafy.fullerting.global.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatResponse {
    private Long chatSenderId; // 전송자 ID
    private String chatMessage; // 채팅 내용
    private String chatRoomId; // 채팅방 ID
}
