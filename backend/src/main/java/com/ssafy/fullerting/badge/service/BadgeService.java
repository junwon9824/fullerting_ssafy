package com.ssafy.fullerting.badge.service;


import com.ssafy.fullerting.badge.model.dto.response.MyBadgeResponse;
import com.ssafy.fullerting.badge.model.entity.Badge;
import com.ssafy.fullerting.badge.repository.BadgeRepository;
import com.ssafy.fullerting.crop.type.model.entity.Crop;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BadgeService {
//    private final BadgeRepository badgeRepository;
//
//    public List<Badge> getAllBadges() {
//        return badgeRepository.findAll();
//    }
//
//    public MyBadgeResponse earnBadgeHandler(CustomUser customUser, Optional<T> missionResult) {
//        // 해당 유저가 처음 미션을 달성하는 경우 이 서비스를 호출한다(이전에 해당 뱃지를 얻은 적이 없는 경우만)
//        // DB조회를 줄이기 위해 다른 서비스로직에서 유저를 조회한 값을 받아서 사용한다
//
//        // mission Result의 데이터타입이 Crop 이면 ..
//        // 마지막 단계까지 다 키운 작물
//
//
//        // 이후 미션 데이터타입에 따라 뱃지 구분..
//    }
//
//    // DB에서 뱃지를 조회한다
//    private Badge getCropBadge(Crop crop) {
//        badgeRepository.findByName(crop.getName());
//    }
}
