package com.ssafy.fullerting.badge.service;


import com.ssafy.fullerting.badge.model.dto.response.MyBadgeResponse;
import com.ssafy.fullerting.badge.model.entity.MyBadge;
import com.ssafy.fullerting.badge.repository.MyBadgeRepository;
import com.ssafy.fullerting.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyBadgeService {

    private final MyBadgeRepository myBadgeRepository;
    private final UserService userService;

    public List<MyBadgeResponse> getAllMyBadges() {
        Long userId = userService.getUserInfo().getId();
        log.info("유저 : {} 의 뱃지 정보조회", userId);
        List<MyBadge> myBadges = myBadgeRepository.findByCustomUser_Id(userId);


        return myBadges.stream().map(myBadge ->
                MyBadgeResponse.builder()
                .id(myBadge.getMyBadgeId())
                .userId(myBadge.getCustomUser().getId())
                .badgeName(myBadge.getBadge())
                .badgeImg()
                .build(); )


    }
}
