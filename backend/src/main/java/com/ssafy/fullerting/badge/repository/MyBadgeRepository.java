package com.ssafy.fullerting.badge.repository;

import com.ssafy.fullerting.badge.model.entity.MyBadge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyBadgeRepository extends JpaRepository<MyBadge, Long> {
//    List<MyBadge> findByCustomUser_Id(Long CustomUserId);
    List<MyBadge> findByCustomUser_Id(Long CustomUserId);


}
