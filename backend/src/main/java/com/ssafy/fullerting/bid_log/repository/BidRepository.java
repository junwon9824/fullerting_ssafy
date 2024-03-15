package com.ssafy.fullerting.bid_log.repository;

import com.ssafy.fullerting.bid_log.model.entity.BidLog;
import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<BidLog,Long > {

}
