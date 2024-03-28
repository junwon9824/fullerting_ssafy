package com.ssafy.fullerting.chat.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CreateChatRoomResponse {
    private Long chatRoomId; //채팅방 id
}
