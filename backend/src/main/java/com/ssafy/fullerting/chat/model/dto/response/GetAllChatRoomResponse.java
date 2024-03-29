package com.ssafy.fullerting.chat.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class GetAllChatRoomResponse {
    private Long chatRoomId; //채팅방 ID
    private String chatRoomOtherThumb; //상대방 프로필 이미지
    private String chatOtherNick; //상대방 닉네임
    private String chatRoomLastMessage; //채팅방 마지막 메시지
    private String chatRoomLastMessageSendAt; //채팅방 마지막 메시지 전송일자
}
