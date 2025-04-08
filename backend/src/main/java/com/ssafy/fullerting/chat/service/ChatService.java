package com.ssafy.fullerting.chat.service;

import com.ssafy.fullerting.chat.model.dto.request.ChatRequest;
import com.ssafy.fullerting.chat.model.dto.response.ChatResponse;
import com.ssafy.fullerting.chat.model.dto.response.GetAllChatResponse;

import java.util.List;

public interface ChatService {
    ChatResponse createChat(Long senderId, ChatRequest chatRequest);
    List<GetAllChatResponse> getAllChat(Long chatRoomId);
}
