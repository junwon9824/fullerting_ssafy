package com.ssafy.fullerting.exArticle.repository;

import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExArticleRepository extends JpaRepository<ExArticle,Long > {
}
