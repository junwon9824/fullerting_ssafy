package com.ssafy.fullerting.deal.controller;

import com.ssafy.fullerting.deal.model.dto.request.DealEndRequest;
import com.ssafy.fullerting.deal.model.dto.request.DealstartRequest;
import com.ssafy.fullerting.user.model.dto.response.UserResponse;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import com.ssafy.fullerting.user.service.UserService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final UserService userService;

    public MessageController(SimpMessagingTemplate messagingTemplate, UserService userService) {
        this.messagingTemplate = messagingTemplate;
        this.userService = userService;
    }

    @PostMapping("/publish") //현재 게시물 거래 시작.
    public void publishAuctionEvent(@RequestBody DealstartRequest dealstartRequest) {
        // 거래제안 이벤트 발행
        CustomUser user = UserResponse.toEntity(userService.getUserInfo());

        String userChannel = "/topic/dealstart-events/" + user.getId() + "/" + dealstartRequest.getId();
        messagingTemplate.convertAndSend(userChannel, dealstartRequest);
    }

    @PostMapping("/deal-end") // 거래 종료 이벤트 발행
    public void publishDealEndEvent(@RequestBody DealEndRequest dealEndRequest) {

        // 거래 종료 이벤트 발행
        CustomUser user = UserResponse.toEntity(userService.getUserInfo());

        String userChannel = "/user/" + user.getId() + "/channel"; // 사용자의 고유한 채널
        messagingTemplate.convertAndSend(userChannel, dealEndRequest);
    }

}
