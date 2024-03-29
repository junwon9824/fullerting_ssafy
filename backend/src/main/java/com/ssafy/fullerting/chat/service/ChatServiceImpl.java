package com.ssafy.fullerting.chat.service;

import com.ssafy.fullerting.chat.exception.ChatException;
import com.ssafy.fullerting.chat.model.dto.request.ChatRequest;
import com.ssafy.fullerting.chat.model.dto.response.ChatResponse;
import com.ssafy.fullerting.chat.model.entity.Chat;
import com.ssafy.fullerting.chat.model.entity.ChatRoom;
import com.ssafy.fullerting.chat.repository.ChatRepository;
import com.ssafy.fullerting.chat.repository.ChatRoomRepository;
import com.ssafy.fullerting.user.exception.UserErrorCode;
import com.ssafy.fullerting.user.exception.UserException;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import com.ssafy.fullerting.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.ssafy.fullerting.chat.exception.ChatErrorCode.NOT_EXISTS_CHAT_ROOM;
import static com.ssafy.fullerting.chat.exception.ChatErrorCode.TRANSACTION_FAIL;
import static com.ssafy.fullerting.user.exception.UserErrorCode.NOT_EXISTS_USER;

@RequiredArgsConstructor
@Service
@Slf4j
public class ChatServiceImpl implements ChatService{
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    @Override
    public ChatResponse createChat(Long senderId, ChatRequest chatRequest) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRequest.getChatRoomId()).orElseThrow(()->new ChatException(NOT_EXISTS_CHAT_ROOM));
        try {
            //채팅 내역 DB 저장
            Chat chat =chatRepository.save(Chat.builder()
                    .chatRoom(chatRoom)
                    .senderId(senderId)
                    .message(chatRequest.getChatMessage())
                    .sendAt(Timestamp.valueOf(LocalDateTime.now()))
                    .build());
            CustomUser customUser = userRepository.findById(senderId).orElseThrow(()->new UserException(NOT_EXISTS_USER));

            ChatResponse chatResponse = ChatResponse.toResponse(chat);
            chatResponse = chatResponse.toBuilder()
                    .chatSenderThumb(customUser.getThumbnail())
                    .chatSenderNick(customUser.getNickname())
                    .build();

            return chatResponse;
        } catch(Exception e){
            throw new ChatException(TRANSACTION_FAIL);
        }
    }
}
