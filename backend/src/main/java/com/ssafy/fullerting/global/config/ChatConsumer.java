//package com.ssafy.fullerting.global.config;
//
//import com.ssafy.fullerting.chat.model.dto.response.ChatResponse;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ChatConsumer {
////    private final SimpMessagingTemplate messagingTemplate;
//
////    public ChatConsumer(SimpMessagingTemplate messagingTemplate) {
////        this.messagingTemplate = messagingTemplate;
////    }
//
//    @KafkaListener(topics = "#{T(java.util.List).of('chat-1', 'chat-2')}", groupId = "chat-group") // 예시로 두 개의 채팅방
//    public void listen(ChatResponse chatResponse) {
//        messagingTemplate.convertAndSend("/sub/chat/" + chatResponse.getChatRoomId(), chatResponse);
//    }
//}
