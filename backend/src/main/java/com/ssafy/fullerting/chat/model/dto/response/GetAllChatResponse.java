package com.ssafy.fullerting.chat.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.fullerting.chat.model.entity.Chat;
import com.ssafy.fullerting.chat.model.entity.ChatRoom;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class GetAllChatResponse {
    private Long chatId; //채팅기록 ID
    private Long chatRoomId; //채팅방 ID
    private Long chatSenderId; //전송자 ID
    private String chatSenderThumb; //전송자 프로필 이미지
    private String chatSenderNick; //전송자 닉네임
    private String chatMessage; //내용
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp chatSendAt; //전송일자
}
