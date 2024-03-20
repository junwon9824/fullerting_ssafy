package com.ssafy.fullerting.trans.repository;

import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.trans.model.entity.Trans;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransRepository extends JpaRepository<Trans, Long> {

    Optional <Trans> findByExArticleId(Long aLong);
}
