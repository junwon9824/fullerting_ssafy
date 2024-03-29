package com.ssafy.fullerting.chat.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ChatRequest {
    private Long chatRoomId; //채팅방 ID
    private String chatMessage; //채팅 내용
}
