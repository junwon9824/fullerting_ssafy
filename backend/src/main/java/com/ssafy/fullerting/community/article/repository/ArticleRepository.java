package com.ssafy.fullerting.community.article.repository;

import com.ssafy.fullerting.community.article.model.entity.Article;
import com.ssafy.fullerting.community.article.model.enums.ArticleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query(" select a from Article a where a.type=:type")
    List<Article> findAllByType(ArticleType type);

    @Query(" select a from Article a where a.title like concat('%',:keyword,'%') or a.content like concat('%',:keyword,'%')")

    List<Article> findAllByContentandtitle(String keyword);



}
