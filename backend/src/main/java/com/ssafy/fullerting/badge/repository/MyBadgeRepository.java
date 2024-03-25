package com.ssafy.fullerting.badge.repository;

import com.ssafy.fullerting.badge.model.entity.Badge;
import com.ssafy.fullerting.badge.model.entity.MyBadge;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MyBadgeRepository extends JpaRepository<MyBadge, Long> {
//    List<MyBadge> findByCustomUser_Id(Long CustomUserId);
    List<MyBadge> findByCustomUserId(Long CustomUserId);

    Optional<MyBadge> findByCustomUserAndBadge(CustomUser user, Badge badge);

}
