package com.ssafy.fullerting.chat.service;

import com.ssafy.fullerting.chat.model.dto.request.CreateChatRoomRequest;
import com.ssafy.fullerting.chat.model.dto.response.CreateChatRoomResponse;
import com.ssafy.fullerting.chat.model.dto.response.GetAllChatRoomResponse;
import com.ssafy.fullerting.chat.model.dto.response.GetDetailChatRoomResponse;

import java.util.List;

public interface ChatRoomService {
    CreateChatRoomResponse createChatRoom(CreateChatRoomRequest createChatRoomRequest);
    List<GetAllChatRoomResponse> getAllChatRoom();
    GetDetailChatRoomResponse getDetailChatRoom(Long chatRoomId);
}
