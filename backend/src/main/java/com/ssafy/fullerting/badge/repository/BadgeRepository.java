package com.ssafy.fullerting.badge.repository;

import com.ssafy.fullerting.badge.model.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {

    Badge findByName(String name);
    Optional<Badge> findBadgeByTrigger(String trigger);
}
