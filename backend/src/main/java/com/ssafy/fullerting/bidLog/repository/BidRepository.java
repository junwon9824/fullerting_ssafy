package com.ssafy.fullerting.bidLog.repository;

import com.ssafy.fullerting.bidLog.model.entity.BidLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BidRepository extends JpaRepository<BidLog,Long > {

    List<BidLog> findAllByDealId(Long bidid);

    @Query("select b  from BidLog b where b.user_id= :userid")
    List<BidLog> findAllByUser_id(Long userid);
}
