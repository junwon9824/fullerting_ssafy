package com.ssafy.fullerting.chat.repository;

import com.ssafy.fullerting.chat.model.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByExArticleIdAndBuyerId(Long exArticleId, Long buyerId);
}
