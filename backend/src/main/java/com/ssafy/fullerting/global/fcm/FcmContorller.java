package com.ssafy.fullerting.global.fcm;


import com.google.firebase.messaging.FirebaseMessagingException;
import com.ssafy.fullerting.global.fcm.model.dto.MessageRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/message")
public class FcmContorller {
    private final FcmService fcmService;

    // fcm를 보낸다 ( topic )
    @PostMapping("/fcm/topic")
    public ResponseEntity sendMessageTopic(@RequestBody MessageRequestDTO requestDTO) throws IOException, FirebaseMessagingException {
        fcmService.sendMessageByTopic(requestDTO.getTitle(), requestDTO.getBody());
        return ResponseEntity.ok().build();
    }
    // fcm를 보낸다 ( token )
    @PostMapping("/fcm/token")
    public ResponseEntity sendMessageToken(@RequestBody MessageRequestDTO requestDTO) throws IOException, FirebaseMessagingException{
        fcmService.sendMessageByToken(requestDTO.getTitle(), requestDTO.getBody(), requestDTO.getTargetToken());
        return ResponseEntity.ok().build();
    }

}
