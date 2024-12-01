//package com.ssafy.fullerting.global.config;
//
//import com.ssafy.fullerting.chat.model.dto.response.ChatResponse;
//import com.ssafy.fullerting.security.model.entity.CustomAuthenticationToken;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
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
//
//
//    @MessageMapping("/chat")
//    public void chatBroker(SimpMessageHeaderAccessor headerAccessor, ChatRequest chatRequest) {
//        // 클라이언트가 보내는 정보
//        // 채팅방 ID, 전송자 ID, 채팅 내용
//        // 클라이언트에게 보내는 정보
//        // 채팅방 ID, 전송자 정보(ID, 프로필 사진, 닉네임), 채팅 내용, 전송일자
//
//        // 세션 속성에서 CustomAuthenticationToken 조회
//        CustomAuthenticationToken authentication = (CustomAuthenticationToken) headerAccessor.getSessionAttributes().get("userAuthentication");
//
//        if (authentication != null) {
//            Long senderId = authentication.getUserId();
//
//            //채팅 내역 저장
//            ChatResponse chatResponse = chatService.createChat(senderId, chatRequest);
//
//            //sub 구독자에게 채팅 전달
//            messagingTemplate.convertAndSend("/sub/chat/"+ chatRequest.getChatRoomId(),chatResponse); //1:1 채팅방으로
////            chatProducerService.sendMessage(chatRequest); // "chat-topic"은 Kafka의 토픽 이름
//
//            log.info("Message : [{}], sendID : [{}], ChatRoom : [{}]", chatResponse.getChatMessage(), chatResponse.getChatSenderId(), chatResponse.getChatRoomId());
//        } else {
//            log.error("NOT_EXISTS_SENDER");
//        }
//
//    }
//}
