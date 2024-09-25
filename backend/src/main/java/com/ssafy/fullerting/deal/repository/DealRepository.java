package com.ssafy.fullerting.deal.repository;

import com.ssafy.fullerting.deal.model.entity.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DealRepository extends JpaRepository<Deal,Long > {
    @Query("select d  from Deal d where d.exArticle.user.id=:userid and d.exArticle.deal is not null ")
    List<Deal> findAllMyDeal(Long userid);

    @Query("select d  from Deal d where  d.exArticle.deal is not null ")
    List<Deal> findAllDeal( );

}
