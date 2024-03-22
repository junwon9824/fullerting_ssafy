package com.ssafy.fullerting.trans.repository;

import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.trans.model.entity.Trans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransRepository extends JpaRepository<Trans, Long> {

    Optional<Trans> findByExArticleId(Long aLong);

    @Query("select t from  Trans t    where t.exArticle.trans is not null and t. exArticle.user.id=:userid")
    List<Trans> findAllTrans(Long userid);

}
