package com.ssafy.fullerting.deal.service;

import com.ssafy.fullerting.bidLog.model.dto.response.BidLogResponse;
import com.ssafy.fullerting.bidLog.model.entity.BidLog;
import com.ssafy.fullerting.bidLog.repository.BidRepository;
import com.ssafy.fullerting.deal.model.dto.request.DealProposeRequest;
import com.ssafy.fullerting.deal.model.dto.response.DealResponse;
import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.deal.repository.DealRepository;
import com.ssafy.fullerting.exArticle.exception.ExArticleErrorCode;
import com.ssafy.fullerting.exArticle.exception.ExArticleException;
import com.ssafy.fullerting.exArticle.model.dto.request.ExArticleRegisterRequest;
import com.ssafy.fullerting.exArticle.model.dto.response.ExArticleResponse;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.repository.ExArticleRepository;
import com.ssafy.fullerting.global.utils.MessageUtils;
import com.ssafy.fullerting.trans.model.dto.response.TransResponse;
import com.ssafy.fullerting.user.model.dto.response.UserResponse;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import com.ssafy.fullerting.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DealService {
    private final DealRepository dealRepository;
    private final BidRepository bidRepository;
    private final ExArticleRepository exArticleRepository;
    private final UserService userService;


    public List<DealResponse> selectDeals() {
        UserResponse userResponse = userService.getUserInfo();
        CustomUser customUser = userResponse.toEntity(userResponse);

        List<Deal> deals = dealRepository.findAllMyDeal(customUser.getId());

        return deals.stream().map(deal -> {
            return deal.toResponse(customUser);
        }).collect(Collectors.toList());
    }

    public List<ExArticleResponse> mybidarticles() {
        UserResponse userResponse = userService.getUserInfo();
        CustomUser customUser = userResponse.toEntity(userResponse);

        List<BidLog> bidLogs = bidRepository.findAllByUser_id(customUser.getId());

        List<ExArticleResponse> exArticleResponses = bidLogs.stream().map(bidLog -> {
                    ExArticle article = bidLog.getDeal().getExArticle();
                    return article.toResponse(article, customUser);
                }).
                collect(Collectors.toList());

        return exArticleResponses;
    }

}
