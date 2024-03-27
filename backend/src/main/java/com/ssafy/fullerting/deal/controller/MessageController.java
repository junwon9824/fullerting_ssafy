package com.ssafy.fullerting.deal.controller;

import com.ssafy.fullerting.bidLog.model.dto.request.BidProposeRequest;
import com.ssafy.fullerting.bidLog.service.BidService;
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
import org.springframework.messaging.simp.annotation.SubscribeMapping;
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
    private final BidService bidService;

    @MessageMapping("/chattings/{chattingRoomId}/messages")
    public void chat(@DestinationVariable Long chattingRoomId, DealstartRequest dealstartRequest) {

        bidService.dealbid(chattingRoomId, BidProposeRequest.builder()
                .dealCurPrice(dealstartRequest.getDealCurPrice())
                .userId(dealstartRequest.getUserId())
                .build());

        messagingTemplate.convertAndSend("/sub/chattings/" + chattingRoomId, dealstartRequest);


        log.info("Message [{}] send by member: {} to chatting room: {}", dealstartRequest.getDealCurPrice(), chattingRoomId);

    }

//    @SubscribeMapping("/sub/chattings/{chattingRoomId}")
//    public void subscribeToChatRoom(@DestinationVariable Long chattingRoomId) {
//        // 해당 채널을 구독하는 메서드 내용 추가
//        log.info("User subscribed to chatting room: {}", chattingRoomId);
//    }

}
