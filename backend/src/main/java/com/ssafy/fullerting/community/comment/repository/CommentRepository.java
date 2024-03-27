package com.ssafy.fullerting.community.comment.repository;

import com.ssafy.fullerting.community.comment.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
