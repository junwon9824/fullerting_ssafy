package com.ssafy.fullerting.bidLog.repository;

import com.ssafy.fullerting.bidLog.model.entity.BidLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends MongoRepository<BidLog, String> {

    @Query("{ 'deal.id' : ?0 }")
    List<BidLog> findAllByDealId(String dealId);

    @Query("{ 'userId' : ?0 }")
    List<BidLog> findAllByUserId(Long userId);

    @Query(value = "{ 'deal.exArticle.id' : ?0 }", count = true)
    long countDistinctUserIdsByExArticleId(String exArticleId);

    List<BidLog> findByDealId(String id);

    @Query(sort = "{ 'localDateTime' : -1 }")
    List<BidLog> findTop10ByDealIdOrderByLocalDateTimeDesc(String dealId);
}