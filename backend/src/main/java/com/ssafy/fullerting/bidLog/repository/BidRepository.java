package com.ssafy.fullerting.bidLog.repository;

import com.ssafy.fullerting.bidLog.model.entity.BidLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidRepository extends MongoRepository<BidLog, String>, BidRepositoryCustom { // 사용자 정의 인터페이스 추가

    List<BidLog> findAllByDealId(String dealId);

    @Query("{ 'userId': ?0 }")
    List<BidLog> findAllByUserId(String userId);

    @Query(value = "{ 'deal.exArticle.id': ?0 }", count = true)
    int countDistinctUserIdsByExArticleId(String exArticleId);
}
