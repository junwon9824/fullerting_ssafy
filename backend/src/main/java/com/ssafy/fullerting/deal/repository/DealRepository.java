package com.ssafy.fullerting.deal.repository;

import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DealRepository extends JpaRepository<Deal,Long > {
    @Query("select d  from Deal d where d.exArticle.user.id=:userid and d.exArticle.deal is not null ")
    List<Deal> findAllMyDeal(Long userid);
}
