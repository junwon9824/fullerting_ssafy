package com.ssafy.fullerting.deal.service;

import com.ssafy.fullerting.bidLog.model.dto.response.BidLogResponse;
import com.ssafy.fullerting.bidLog.model.entity.BidLog;
import com.ssafy.fullerting.bidLog.repository.BidRepository;
import com.ssafy.fullerting.deal.model.dto.request.DealProposeRequest;
import com.ssafy.fullerting.deal.model.dto.response.DealResponse;
import com.ssafy.fullerting.deal.model.dto.response.MyExArticleResponse;
import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.deal.repository.DealRepository;
import com.ssafy.fullerting.exArticle.exception.ExArticleErrorCode;
import com.ssafy.fullerting.exArticle.exception.ExArticleException;
import com.ssafy.fullerting.exArticle.model.dto.request.ExArticleRegisterRequest;
import com.ssafy.fullerting.exArticle.model.dto.response.ExArticleAllResponse;
import com.ssafy.fullerting.exArticle.model.dto.response.ExArticleResponse;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.repository.ExArticleRepository;
import com.ssafy.fullerting.global.utils.MessageUtils;
import com.ssafy.fullerting.trans.model.dto.response.TransResponse;
import com.ssafy.fullerting.user.model.dto.response.UserResponse;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import com.ssafy.fullerting.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.m;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DealService {
    private final DealRepository dealRepository;
    private final BidRepository bidRepository;
    private final ExArticleRepository exArticleRepository;
    private final UserService userService;


    public List<ExArticleAllResponse> selectDeals() {
        UserResponse userResponse = userService.getUserInfo();
        CustomUser customUser = userResponse.toEntity(userResponse);

        List<Deal> deals = dealRepository.findAllDeal();

        return deals.stream().map(deal -> {
            ExArticleAllResponse exArticleAllResponse = ExArticleAllResponse.builder()
                    .exArticleResponse(deal.getExArticle().toResponse(deal.getExArticle(), deal.getExArticle().getUser()))
                    .packDiaryResponse(deal.getExArticle().getPackDiary() == null ? null : deal.getExArticle().getPackDiary().toResponse(deal.getExArticle().getPackDiary()))
                    .build();

            return exArticleAllResponse;
        }).collect(Collectors.toList());

    }
    @Transactional
    public List<MyExArticleResponse> mybidarticles() {
        UserResponse userResponse = userService.getUserInfo();
        CustomUser customUser = userResponse.toEntity(userResponse);

        List<BidLog> bidLogs = bidRepository.findAllByuserId(customUser.getId());

        // 중복을 제거할 열의 값을 저장할 Set
        HashSet<Long> exArticleIds = new HashSet<>();

        // 중복 제거된 결과를 저장할 리스트
        List<MyExArticleResponse> uniqueResponses = new ArrayList<>();

        // 특정 열의 값을 추출하여 Set에 저장하여 중복 제거
        for (BidLog bidLog : bidLogs) {
            ExArticle article = bidLog.getDeal().getExArticle();
            Long exArticleId = article.getId(); // 특정 열의 값 추출
            if (!exArticleIds.contains(exArticleId)) {
                // 중복이 아닌 경우에만 리스트에 추가
                exArticleIds.add(exArticleId);
                uniqueResponses.add(article.toMyResponse(article, customUser));
            }
        }

        return uniqueResponses;
    }

}
