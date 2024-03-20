package com.ssafy.fullerting.exArticle.repository;

import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExArticleRepository extends JpaRepository<ExArticle,Long > {
    Optional<List<ExArticle>> findAllByTitleContaining(String keyword);
     List<ExArticle> findAllByType(ExArticleType type);

}
