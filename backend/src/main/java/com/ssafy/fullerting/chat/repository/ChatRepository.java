package com.ssafy.fullerting.chat.repository;

import com.ssafy.fullerting.chat.model.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByChatRoomId(Long chatRoomId);
    //가장 최근 Chat 조회
    @Query("SELECT c FROM Chat c WHERE c.chatRoom.id = :chatRoomId ORDER BY c.sendAt DESC")
    Chat findLatestChatByChatRoomId(@Param("chatRoomId") Long chatRoomId);
}
