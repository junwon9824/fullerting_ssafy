package com.ssafy.fullerting.chat.service;

import com.ssafy.fullerting.chat.model.dto.request.CreateChatRoomRequest;
import com.ssafy.fullerting.chat.model.dto.response.CreateChatRoomResponse;

public interface ChatRoomService {
    CreateChatRoomResponse createChatRoom(CreateChatRoomRequest createChatRoomRequest);
}
