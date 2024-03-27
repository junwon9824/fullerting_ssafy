package com.ssafy.fullerting.community.love.repository;

import com.ssafy.fullerting.community.article.model.Article;
import com.ssafy.fullerting.community.love.model.Love;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoveRepository extends JpaRepository<Love, Long> {

}
