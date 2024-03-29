package com.ssafy.fullerting.chat.service;

import com.ssafy.fullerting.chat.model.dto.request.ChatRequest;
import com.ssafy.fullerting.chat.model.dto.response.ChatResponse;

public interface ChatService {
    ChatResponse createChat(Long senderId, ChatRequest chatRequest);
}
