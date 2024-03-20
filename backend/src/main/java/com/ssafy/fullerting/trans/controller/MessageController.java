package com.ssafy.fullerting.trans.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;

    public MessageController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/publish")
    public void publishMessage() {
        // "/topic/messages" 채널로 메시지 발행
        messagingTemplate.convertAndSend("/topic/messages", "Hello, subscribers!");

    }

}
