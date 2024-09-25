package com.ssafy.fullerting.global.config;

import com.ssafy.fullerting.chat.model.dto.request.ChatRequest;
import com.ssafy.fullerting.chat.model.dto.response.ChatResponse;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatProducerService {
    private final KafkaTemplate<String, ChatResponse> kafkaTemplate;

    public ChatProducerService(KafkaTemplate<String, ChatResponse> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // Kafka로 메시지 전송
    public void sendMessage(Long senderId, ChatRequest chatRequest) {
//        ChatResponse chatResponse = new ChatResponse(senderId, chatRequest.getChatMessage(), chatRequest.getChatRoomId());
        ChatResponse chatResponse =ChatResponse.builder()
                .chatSenderId(senderId)
                .chatMessage(chatRequest.getChatMessage())
                .chatRoomId(chatRequest.getChatRoomId())
                .build();
        // 채팅방 ID에 따라 동적으로 토픽 이름 생성
        String topicName = "chat-" + chatRequest.getChatRoomId();

        // Kafka에 메시지 전송
        kafkaTemplate.send(topicName, String.valueOf(chatResponse.getChatRoomId()), chatResponse);
    }
}
