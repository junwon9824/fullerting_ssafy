package com.ssafy.fullerting.community.article.repository;

import com.ssafy.fullerting.community.article.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
