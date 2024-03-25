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
    @JoinColumn(name = "ex_article_id")
    private ExArticle exArticle; //작물거래

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CustomUser customUser; //보내는 사람

    @Column(name = "to_user_id")
    @NotNull
    private int toUserId; //받는 사람

    @Column(name = "chat_content")
    @NotNull
    private String content; //내용

    @Column(name = "chat_send_at")
    @NotNull
    private Timestamp sendAt; //보낸시간
}
