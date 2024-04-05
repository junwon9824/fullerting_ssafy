package com.ssafy.fullerting.chat.repository;

import com.ssafy.fullerting.chat.model.entity.Chat;
import com.ssafy.fullerting.chat.model.entity.ChatRoom;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByExArticleAndBuyer(ExArticle exArticle, CustomUser buyer);
    @Query("SELECT cr FROM ChatRoom cr WHERE cr.buyer = :user OR cr.exArticle.user = :user")
    List<ChatRoom> findByBuyerOrExArticleUser(@Param("user") CustomUser user);

}
