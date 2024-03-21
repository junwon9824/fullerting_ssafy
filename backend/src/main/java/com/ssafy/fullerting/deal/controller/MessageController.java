package com.ssafy.fullerting.deal.controller;

import com.ssafy.fullerting.deal.model.dto.request.DealEndRequest;
import com.ssafy.fullerting.deal.model.dto.request.DealstartRequest;
import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.user.model.dto.response.UserResponse;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import com.ssafy.fullerting.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final UserService userService;


        @MessageMapping("/chattings/{chattingRoomId}/messages")  // (2)
        public void chat(@DestinationVariable Long chattingRoomId, DealstartRequest dealstartRequest) {  // (3)
            messagingTemplate.convertAndSend("/sub/chattings/" + chattingRoomId, dealstartRequest.getContent());
            log.info("Message [{}] send by member: {} to chatting room: {}", dealstartRequest.getContent(), dealstartRequest.getSenderid(), chattingRoomId);
        }

}
