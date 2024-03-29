package com.ssafy.fullerting.community.comment.repository;

import com.ssafy.fullerting.community.comment.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByArticle_Id(Long article_id);
}
