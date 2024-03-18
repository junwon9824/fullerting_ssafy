package com.ssafy.fullerting.exArticle.repository;

import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExArticleRepository extends JpaRepository<ExArticle,Long > {
    Optional<ExArticle> findAllByTitleContaining(String keyword);
}
