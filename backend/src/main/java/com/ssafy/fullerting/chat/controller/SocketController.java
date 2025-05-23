//package com.ssafy.fullerting.chat.controller;
//
//import com.ssafy.fullerting.alarm.model.EventAlarmType;
//import com.ssafy.fullerting.alarm.model.dto.request.AlarmPayload;
//import com.ssafy.fullerting.alarm.service.EventAlarmNotificationService;
//import com.ssafy.fullerting.alarm.service.EventAlarmService;
//import com.ssafy.fullerting.chat.model.dto.request.ChatRequest;
//import com.ssafy.fullerting.chat.model.dto.response.ChatResponse;
//import com.ssafy.fullerting.chat.service.ChatRoomService;
//import com.ssafy.fullerting.chat.service.ChatService;
////import com.ssafy.fullerting.global.config.ChatProducerService;
//import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
//import com.ssafy.fullerting.exArticle.service.ExArticleService;
//import com.ssafy.fullerting.security.model.entity.CustomAuthenticationToken;
//import com.ssafy.fullerting.user.model.dto.response.UserResponse;
//import com.ssafy.fullerting.user.model.entity.MemberProfile;
//import com.ssafy.fullerting.user.service.UserService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//
//@Controller
//@Slf4j
//@RequiredArgsConstructor
//public class SocketController {
//    private final SimpMessagingTemplate messagingTemplate;
//    private final ChatService chatService;
//    private final ChatRoomService chatRoomService;
//    private final EventAlarmNotificationService eventAlarmNotificationService;
//    private final EventAlarmService eventAlarmService;
//    private final UserService userService;
//    private final ExArticleService exArticleService;
////    private final ChatProducerService chatProducerService;
//
//
//    /**
//     * 채팅 전송
//     *
//     * @param headerAccessor
//     * @param chatRequest
//     */
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
//            log.info("채팅 전송자 : {}", senderId);
//
//            //채팅 내역 저장
//            ChatResponse chatResponse = chatService.createChat(senderId, chatRequest);
//
//            //sub 구독자에게 채팅 전달
//            messagingTemplate.convertAndSend("/sub/chat/"+ chatRequest.getChatRoomId(),chatResponse); //1:1 채팅방으로
////            chatProducerService.sendMessage(chatRequest); // "chat-topic"은 Kafka의 토픽 이름
//
//            long chatroomid = chatRequest.getChatRoomId();
//            long author=chatRoomService.getDetailChatRoom(chatroomid).getChatRoomUserId();
//            MemberProfile memberProfile=  userService.getUserEntityById(author);
//
////            MemberProfile authorProfile=MemberProfile.builder()
////                    .authProvider( memberProfile.getAuthProvider())
////                    .rank(memberProfile.getRank())
////                    .role(memberProfile.getRole())
////                    .id(memberProfile.getId())
////                    .email(memberProfile.getEmail())
////                    .thumbnail(memberProfile.getThumbnail())
////                    .location(memberProfile.getLocation())
////                    .nickname(memberProfile.getNickname())
////                    .build();
//
//            long articleid = chatRoomService.getDetailChatRoom(chatroomid).getChatRoomExArticleId();
//            String redirectURL = "/trade/" + chatroomid+ "/chat";
//
//            eventAlarmService.notifyChatRoomAuthor(memberProfile,  exArticleService.getbyid(articleid) , redirectURL  );
//
//            log.info("Message : [{}], sendID : [{}], ChatRoom : [{}]", chatResponse.getChatMessage(), chatResponse.getChatSenderId(), chatResponse.getChatRoomId());
//        } else {
//            log.error("NOT_EXISTS_SENDER");
//        }
//
//    }
//}
