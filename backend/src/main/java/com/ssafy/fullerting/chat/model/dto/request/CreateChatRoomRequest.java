package com.ssafy.fullerting.chat.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CreateChatRoomRequest {
    private Long exArticleId; //거래 게시글 id
    private String redirectURL; //채팅방 리다이렉트 URL (알림)
}
