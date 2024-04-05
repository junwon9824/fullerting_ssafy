package com.ssafy.fullerting.bidLog.repository;

import com.ssafy.fullerting.bidLog.model.entity.BidLog;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<BidLog,Long > {

    List<BidLog> findAllByDealId(Long bidid);

    @Query("select b  from BidLog b where b.userId= :userid")
    List<BidLog> findAllByuserId(Long userid);

    @Query("SELECT COUNT(DISTINCT b.userId) FROM BidLog b WHERE b.deal.exArticle.id = :exArticleId")
    int countDistinctUserIdsByExArticleId(@Param("exArticleId") Long exArticleId);

    @Query("SELECT MAX(bl.bidLogPrice) FROM BidLog bl WHERE bl.deal.exArticle.id = :exArticleId")
    Optional<Integer> findMaxBidPriceByExArticleId(@Param("exArticleId") Long exArticleId);
}
