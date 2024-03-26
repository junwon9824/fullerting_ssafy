package com.ssafy.fullerting.bidLog.service;

import com.ssafy.fullerting.bidLog.exception.BidErrorCode;
import com.ssafy.fullerting.bidLog.exception.BidException;
import com.ssafy.fullerting.bidLog.model.dto.request.BidProposeRequest;
import com.ssafy.fullerting.bidLog.model.dto.response.BidLogResponse;
import com.ssafy.fullerting.bidLog.model.entity.BidLog;
import com.ssafy.fullerting.bidLog.repository.BidRepository;
import com.ssafy.fullerting.deal.exception.DealErrorCode;
import com.ssafy.fullerting.deal.exception.DealException;
import com.ssafy.fullerting.deal.model.dto.request.DealProposeRequest;
import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.deal.repository.DealRepository;
import com.ssafy.fullerting.exArticle.exception.ExArticleErrorCode;
import com.ssafy.fullerting.exArticle.exception.ExArticleException;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType;
import com.ssafy.fullerting.exArticle.repository.ExArticleRepository;
import com.ssafy.fullerting.user.model.dto.response.UserResponse;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import com.ssafy.fullerting.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.util.privilegedactions.LoadClass;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BidService {
    private final BidRepository bidRepository;
    private final DealRepository dealRepository;
    private final ExArticleRepository exArticleRepository;

    private final UserService userService;

    public void deal(BidProposeRequest bidProposeRequest, CustomUser user, Long ex_article_id) {
        LocalDateTime time = LocalDateTime.now();


        ExArticle exArticle = exArticleRepository.findById(ex_article_id).orElseThrow
                (() -> new ExArticleException(ExArticleErrorCode.NOT_EXISTS));

        Long dealid = exArticle.getDeal().getId();

        Deal deal = dealRepository.findById(dealid).orElseThrow(
                () -> new DealException(DealErrorCode.NOT_EXISTS));

        BidLog bidLog = bidRepository.save(BidLog.builder()
                .bid_log_price(bidProposeRequest.getDealCurPrice())
                .localDateTime(time)
                .user_id(user.getId())
                .deal(deal)
                .build());

    }

    public List<BidLogResponse> selectbid(Long ex_article_id) {
        ExArticle exArticle = exArticleRepository.findById(ex_article_id).orElseThrow(() ->
                new ExArticleException(ExArticleErrorCode.NOT_EXISTS));

        if(!exArticle.getType().equals(ExArticleType.DEAL)){
            throw new BidException(BidErrorCode.NOT_DEAL);
        }

        List<BidLog> bidLog = bidRepository.findAllByDealId(exArticle.getDeal().getId());

        List<BidLogResponse> bidLogResponses = bidLog.stream().map(bidLog1 -> {
                    return bidLog1.tobidLogResponse(bidLog1);
                })
                .collect(Collectors.toList());

        return bidLogResponses;

    }

    public void dealbid(Long exArticleId, BidProposeRequest bidProposeRequest) {

        UserResponse userResponse = userService.getUserInfo();
        CustomUser customUser = userResponse.toEntity(userResponse);

        ExArticle exArticle = exArticleRepository.findById(exArticleId).orElseThrow(() -> new ExArticleException(
                ExArticleErrorCode.NOT_EXISTS));

        if (exArticle.getDeal() == null) {
            throw new BidException(BidErrorCode.NOT_DEAL);
        }

        Deal deal = dealRepository.findById(exArticle.getDeal().getId()).orElseThrow(() ->
                new DealException(DealErrorCode.NOT_EXISTS));

        bidRepository.save(BidLog.builder()
                .bid_log_price(bidProposeRequest.getDealCurPrice())
                .deal(deal)
                .user_id(customUser.getId())
                .localDateTime(LocalDateTime.now())
                .build());


    }
}
