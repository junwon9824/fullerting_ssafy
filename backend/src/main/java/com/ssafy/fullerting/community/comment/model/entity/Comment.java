package com.ssafy.fullerting.community.comment.model.entity;

import com.ssafy.fullerting.community.article.model.entity.Article;
import com.ssafy.fullerting.community.comment.model.dto.response.CommentResonse;
import com.ssafy.fullerting.user.model.entity.MemberProfile;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@ToString
@Table(name = "comment")

public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private MemberProfile customUser;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    private String comment_content;

    @Column(name = "comment_created_at")
    private LocalDateTime localDateTime;

    public CommentResonse tocommentResonse(MemberProfile customUser) {
        return CommentResonse.builder()
                .commentcontent(this.comment_content)
                .rank(customUser.getRank())
                .thumbnail(customUser.getThumbnail())
                .nickname(customUser.getNickname())
                .localDateTime(this.getLocalDateTime())
                .id(this.id)
                .authorid(this.customUser.getId())
                .build();
    }
}
