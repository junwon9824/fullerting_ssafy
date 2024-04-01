package com.ssafy.fullerting.chat.model.entity;

import com.ssafy.fullerting.crop.type.model.entity.Crop;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "chat")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@ToString
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom; //채팅방

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CustomUser sender; //보낸 사람

    @Column(name = "chat_message", length = 5000)
    @NotNull
    private String message; //내용

    @Column(name = "chat_send_at")
    @NotNull
    private Timestamp sendAt; //전송일자
}
