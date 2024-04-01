package com.ssafy.fullerting.community.comment.model.entity;

import com.ssafy.fullerting.community.article.model.entity.Article;
import com.ssafy.fullerting.community.comment.model.dto.response.CommentResonse;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
    private CustomUser customUser;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    private String comment_content;

    @Column(name = "comment_created_at")
    private LocalDateTime localDateTime;

    public CommentResonse tocommentResonse(CustomUser customUser) {
        return CommentResonse.builder()
                .commentcontent(this.comment_content)
                .rank(customUser.getRank())
                .thumbnail(customUser.getThumbnail())
                .nickname(customUser.getNickname())
                .localDateTime(this.getLocalDateTime())
                .id(this.id)
                .build();
    }
}
