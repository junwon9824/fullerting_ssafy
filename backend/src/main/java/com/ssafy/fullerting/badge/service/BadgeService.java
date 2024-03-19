package com.ssafy.fullerting.badge.service;


import com.ssafy.fullerting.badge.model.entity.Badge;
import com.ssafy.fullerting.badge.repository.BadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BadgeService {
    private final BadgeRepository badgeRepository;

    public List<Badge> getAllBadges() {
        return badgeRepository.findAll();
    }
}
