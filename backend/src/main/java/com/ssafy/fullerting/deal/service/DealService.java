package com.ssafy.fullerting.deal.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ssafy.fullerting.bidLog.model.entity.BidLog;
import com.ssafy.fullerting.bidLog.repository.BidRepository;
import com.ssafy.fullerting.deal.model.dto.response.MyExArticleResponse;
import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.deal.repository.DealRepository;
import com.ssafy.fullerting.exArticle.model.dto.response.ExArticleAllResponse;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.repository.ExArticleRepository;
import com.ssafy.fullerting.user.model.dto.response.UserResponse;
import com.ssafy.fullerting.user.model.entity.MemberProfile;
import com.ssafy.fullerting.user.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DealService {
    private final DealRepository dealRepository;
    private final BidRepository bidRepository;
    private final ExArticleRepository exArticleRepository;
    private final UserService userService;

    public List<ExArticleAllResponse> selectDeals() {

        List<Deal> deals = dealRepository.findAllDeal();

        return deals.stream().map(deal -> {
            ExArticle article = deal.getExArticle();
            return ExArticleAllResponse.builder()
                    .exArticleResponse(article.toResponse(article, article.getUser()))
                    .packDiaryResponse(article.getPackDiary() == null ? null
                            : article.getPackDiary().toResponse(article.getPackDiary()))
                    .build();
        }).collect(Collectors.toList());
    }

    @Transactional
    public List<MyExArticleResponse> mybidarticles() {
        UserResponse userResponse = userService.getUserInfo();
        MemberProfile customUser = UserResponse.toEntity(userResponse);

        List<BidLog> bidLogs = bidRepository.findAllByUserId(customUser.getId());
        HashSet<Long> exArticleIds = new HashSet<>();
        List<MyExArticleResponse> uniqueResponses = new ArrayList<>();

        for (BidLog bidLog : bidLogs) {
            ExArticle article = bidLog.getDeal().getExArticle();
            Long exArticleId = article.getId();
            if (exArticleIds.add(exArticleId)) { // add returns true if element was not present
                uniqueResponses.add(article.toMyResponse(article, customUser));
            }
        }

        return uniqueResponses;
    }

    @Transactional
    public List<MyExArticleResponse> wrotearticles() {
        UserResponse userResponse = userService.getUserInfo();
        MemberProfile customUser = UserResponse.toEntity(userResponse);

        List<ExArticle> exArticles = exArticleRepository.findAllByUserID(customUser.getId());
        HashSet<Long> exArticleIds = new HashSet<>();
        List<MyExArticleResponse> uniqueResponses = new ArrayList<>();

        for (ExArticle exArticle : exArticles) {
            if (exArticle.getDeal() != null) {
                ExArticle article = exArticle.getDeal().getExArticle();
                Long exArticleId = article.getId();

                if (exArticleIds.add(exArticleId)) {
                    uniqueResponses.add(article.toMyResponse(article, customUser));
                }
            }
        }

        return uniqueResponses;
    }
}
