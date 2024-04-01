package com.ssafy.fullerting.chat.service;

import com.ssafy.fullerting.alarm.service.EventAlarmService;
import com.ssafy.fullerting.chat.exception.ChatException;
import com.ssafy.fullerting.chat.model.dto.request.ChatRequest;
import com.ssafy.fullerting.chat.model.dto.response.ChatResponse;
import com.ssafy.fullerting.chat.model.dto.response.GetAllChatResponse;
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
import java.util.List;
import java.util.stream.Collectors;

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
    private final EventAlarmService eventAlarmService;

    @Override
    public ChatResponse createChat(Long senderId, ChatRequest chatRequest) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRequest.getChatRoomId()).orElseThrow(()->new ChatException(NOT_EXISTS_CHAT_ROOM));
        CustomUser sender = userRepository.findById(senderId).orElseThrow(()->new UserException(NOT_EXISTS_USER));
        try {
            //채팅 내역 DB 저장
            Chat chat =chatRepository.save(Chat.builder()
                    .chatRoom(chatRoom)
                    .sender(sender)
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

    @Override
    public List<GetAllChatResponse> getAllChat(Long chatRoomId) {
        List<Chat> chatList = chatRepository.findByChatRoomId(chatRoomId);

        return chatList.stream()
                .map(chat -> {
                    CustomUser sender = userRepository.findById(chat.getSender().getId()).orElseThrow(()->new UserException(NOT_EXISTS_USER)); // senderId로 사용자 조회
                    return GetAllChatResponse.builder()
                            .chatId(chat.getId())
                            .chatRoomId(chat.getChatRoom().getId())
                            .chatSenderId(sender.getId())
                            .chatSenderThumb(sender.getThumbnail())
                            .chatSenderNick(sender.getNickname())
                            .chatMessage(chat.getMessage())
                            .chatSendAt(chat.getSendAt())
                            .build();
                })
                .collect(Collectors.toList());
    }
}
