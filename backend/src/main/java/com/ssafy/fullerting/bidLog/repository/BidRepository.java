package com.ssafy.fullerting.bidLog.repository;

import com.ssafy.fullerting.bidLog.model.entity.BidLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<BidLog, Long>, BidRepositoryCustom { // Long 타입으로 변경


    @Query("SELECT b FROM BidLog b JOIN FETCH b.deal d WHERE d.id = ?1")
    List<BidLog> findAllByDealId(Long dealId); // Deal 엔티티를 함께 가져옴

    @Query("SELECT b FROM BidLog b JOIN FETCH b.deal d WHERE b.userId = ?1")
    List<BidLog> findAllByUserId(Long userId); // Deal 엔티티를 함께 가져옴

    @Query("SELECT COUNT(DISTINCT b.userId) FROM BidLog b WHERE b.deal.exArticle.id = ?1")
    int countDistinctUserIdsByExArticleId(Long exArticleId); // exArticleId의 타입을 Long으로 변경
}
