package com.ssafy.fullerting.global.config;

import com.ssafy.fullerting.chat.model.dto.request.ChatRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KafkaChatProducerController {
    private final ChatProducerService chatProducerService;

    @PostMapping(value = "/pub/chat")
    public ResponseEntity<Void> sendMessage(@RequestBody ChatRequest chatRequest) {
        chatProducerService.sendMessage(chatRequest.getSenderId(), chatRequest);
        return ResponseEntity.ok().build();
    }
}
