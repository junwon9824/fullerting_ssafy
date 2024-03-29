package com.ssafy.fullerting.chat.service;

import com.ssafy.fullerting.chat.model.dto.request.CreateChatRoomRequest;
import com.ssafy.fullerting.chat.model.dto.response.CreateChatRoomResponse;
import com.ssafy.fullerting.chat.model.entity.ChatRoom;
import com.ssafy.fullerting.chat.repository.ChatRoomRepository;
import com.ssafy.fullerting.user.model.dto.response.UserResponse;
import com.ssafy.fullerting.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ChatRoomServiceImpl implements ChatRoomService{
    private final UserService userService;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public CreateChatRoomResponse createChatRoom(CreateChatRoomRequest createChatRoomRequest) {
        UserResponse userResponse = userService.getUserInfo();

        //이미 존재하는 채팅방인지 조회
        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findByExArticleIdAndBuyerId(createChatRoomRequest.getExArticleId(), userResponse.getId());
        //존재하지 않을 경우 채팅방 생성
        if(chatRoomOptional.isEmpty()){
            ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.builder()
                    .exArticleId(createChatRoomRequest.getExArticleId())
                    .buyerId(userResponse.getId())
                    .build());
            return CreateChatRoomResponse.toResponse(chatRoom);
        }
        //존재하는 경우 해당 채팅방 응답
        else{
            return CreateChatRoomResponse.toResponse(chatRoomOptional.get());
        }
    }
}
