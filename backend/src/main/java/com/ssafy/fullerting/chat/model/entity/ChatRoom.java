package com.ssafy.fullerting.chat.model.entity;

import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "chat_room")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@ToString
public class ChatRoom { //채팅방
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;

    @Column(name = "chat_room_ex_article_id")
    @NotNull
    private Long exArticleId; //작물거래 게시글 ID

    @Column(name = "chat_room_buyer_id")
    @NotNull
    private Long buyerId; //구매자

    @Column(name = "chat_room_seller_id")
    private Long sellerId; //판매자
}
