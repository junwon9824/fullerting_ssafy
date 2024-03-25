package com.ssafy.fullerting.badge.service;


import com.ssafy.fullerting.badge.model.dto.response.MyBadgeResponse;
import com.ssafy.fullerting.badge.model.entity.Badge;
import com.ssafy.fullerting.badge.model.entity.MyBadge;
import com.ssafy.fullerting.badge.repository.BadgeRepository;
import com.ssafy.fullerting.badge.repository.MyBadgeRepository;
import com.ssafy.fullerting.record.packdiary.model.entity.PackDiary;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BadgeService {
    private final BadgeRepository badgeRepository;
    private final MyBadgeRepository myBadgeRepository;

    @Transactional
    public MyBadgeResponse earnBadge(PackDiary packDiary) {
        CustomUser user = packDiary.getUser();
        String cropName = packDiary.getCrop().getName();
        int growthStep = packDiary.getGrowthStep();

        Badge targetBadge = badgeRepository.findBadgeByTrigger(cropName + '-' + growthStep).orElseThrow(() ->
                new RuntimeException("해당 조건에 맞는 뱃지 트리거 없음 : " + cropName+'-'+growthStep ));

        Optional<MyBadge> existedBadge = myBadgeRepository.findByCustomUserAndBadge(user, targetBadge);

        if (existedBadge.isPresent()) {
            // 이미 뱃지를 소유하고 있으면 예외 처리
            throw new RuntimeException("사용자가 이미 뱃지를 소유하고 있습니다 : " + targetBadge.getName());
        } else {
            // targetBadge를 MyBadge에 저장
            MyBadge myBadge = new MyBadge();
            myBadge.setCustomUser(user);
            myBadge.setBadge(targetBadge);
            myBadge = myBadgeRepository.save(myBadge); // 저장 후, 반환된 객체 사용
            log.info("작물 4단계 달성 업적 뱃지 저장 : {} ", myBadge.getBadge().toString());


            // 성공적으로 뱃지를 얻었을 때의 응답 생성 및 반환
            MyBadgeResponse myBadgeResponse = new MyBadgeResponse();
            myBadgeResponse.setId(myBadge.getMyBadgeId());
            myBadgeResponse.setBadgeName(targetBadge.getName());
            myBadgeResponse.setBadgeImg(targetBadge.getImg());

            return myBadgeResponse;
        }
    }
}
