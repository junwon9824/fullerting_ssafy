package com.ssafy.fullerting.community.comment.service;

import com.ssafy.fullerting.community.article.exception.ArticleErrorCode;
import com.ssafy.fullerting.community.article.exception.ArticleException;
import com.ssafy.fullerting.community.article.model.entity.Article;
import com.ssafy.fullerting.community.article.repository.ArticleRepository;
import com.ssafy.fullerting.community.comment.exception.CommentErrorCode;
import com.ssafy.fullerting.community.comment.exception.CommentException;
import com.ssafy.fullerting.community.comment.model.dto.request.RegisterCommentRequest;
import com.ssafy.fullerting.community.comment.model.dto.response.CommentResonse;
import com.ssafy.fullerting.community.comment.model.entity.Comment;
import com.ssafy.fullerting.community.comment.repository.CommentRepository;
import com.ssafy.fullerting.user.model.dto.response.UserResponse;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import com.ssafy.fullerting.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.dialect.unique.CreateTableUniqueDelegate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;


    public void registcomment(Long article_id, RegisterCommentRequest registerCommentRequest) {


        Article article = articleRepository.findById(article_id).orElseThrow(() -> new
                ArticleException(ArticleErrorCode.NOT_EXISTS));

        UserResponse userResponse = userService.getUserInfo();
        CustomUser customUser = userResponse.toEntity(userResponse);

        commentRepository.save(Comment.builder()
                .article(article)
                .comment_content(registerCommentRequest.getCommentcontent())
                .customUser(customUser)
                .build());
    }


    public List<CommentResonse> allcomment() {
        return commentRepository.findAll()
                .stream().map(comment -> {
                    CommentResonse commentResonse = comment.tocommentResonse();
                    return commentResonse;
                }).collect(Collectors.toList());
    }


    public void deletecommentbyid(Long articleId, Long commentId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new
                ArticleException(ArticleErrorCode.NOT_EXISTS));

        UserResponse userResponse = userService.getUserInfo();
        CustomUser customUser = userResponse.toEntity(userResponse);

        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new CommentException(CommentErrorCode.NOT_EXISTS));

        if (comment.getCustomUser().getId() != customUser.getId()) {
            throw new CommentException(CommentErrorCode.NOT_MINE);
        }

        commentRepository.delete(comment);

    }
}
