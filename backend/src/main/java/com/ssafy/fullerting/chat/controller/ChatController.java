package com.ssafy.fullerting.chat.controller;

import com.ssafy.fullerting.chat.service.ChatService;
import com.ssafy.fullerting.global.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/chat")
@RequiredArgsConstructor //생성자 주입
@Slf4j
public class ChatController {
    private final ChatService chatService;

    /**
     * 채팅기록 전체조회
     * @param chatRoomId
     * @return
     */
    @GetMapping("/{chat_room_id}")
    public ResponseEntity<MessageUtils> getAllChat(@PathVariable(value = "chat_room_id") Long chatRoomId) {
        return ResponseEntity.ok().body(MessageUtils.success(chatService.getAllChat(chatRoomId)));
    }
}
