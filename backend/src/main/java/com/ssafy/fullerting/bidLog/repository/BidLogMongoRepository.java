package com.ssafy.fullerting.bidLog.repository;

import com.ssafy.fullerting.bidLog.model.entity.BidLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidLogMongoRepository extends MongoRepository<BidLog, String>, BidRepositoryCustom {

    // Deal ID로 조회
    List<BidLog> findAllByDealId(Long dealId);

    // User ID로 조회
    List<BidLog> findAllByUserId(Long userId);

    int countDistinctUserIdByDealId(Long dealId);

}
