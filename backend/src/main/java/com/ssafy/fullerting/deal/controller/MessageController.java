package com.ssafy.fullerting.deal.controller;

import com.ssafy.fullerting.bidLog.model.dto.request.BidProposeRequest;
import com.ssafy.fullerting.bidLog.service.BidService;
import com.ssafy.fullerting.deal.model.dto.request.DealstartRequest;
import com.ssafy.fullerting.security.model.entity.CustomAuthenticationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final BidService bidService;

    @MessageMapping("/bidding/{chattingRoomId}/messages")
    public void bidBroker(@DestinationVariable Long chattingRoomId, SimpMessageHeaderAccessor headerAccessor, DealstartRequest dealstartRequest) {
        // 세션 속성에서 CustomAuthenticationToken 조회
        CustomAuthenticationToken authentication = (CustomAuthenticationToken) headerAccessor.getSessionAttributes().get("userAuthentication");

        if (authentication != null) {
            // 여기에서 authentication.getUserId() 등을 통해 사용자 ID를 사용할 수 있음
            Long userId = authentication.getUserId();
            log.info("웹소켓에서 추출한 유저 id : {}", userId);

            bidService.socketdealbid(chattingRoomId,
                    BidProposeRequest.builder()
                            .dealCurPrice(dealstartRequest.getDealCurPrice())
                            .userId(userId)
                            .build());

            dealstartRequest.setUserId(userId);
//
            messagingTemplate.convertAndSend("/sub/bidding/" + chattingRoomId, dealstartRequest);
            log.info("Message [{}] send by member: {} to chatting room: {}", dealstartRequest.getDealCurPrice(), chattingRoomId);

        } else {
            log.error("웹소켓 요청에 유저 정보없음");
        }
    }

//    @SubscribeMapping("/sub/chattings/{chattingRoomId}")
//    public void subscribeToChatRoom(@DestinationVariable Long chattingRoomId) {
//        // 해당 채널을 구독하는 메서드 내용 추가
//        log.info("User subscribed to chatting room: {}", chattingRoomId);
//    }

}
