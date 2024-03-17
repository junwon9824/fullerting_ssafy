package com.ssafy.fullerting.bid_log.service;

import com.ssafy.fullerting.bid_log.model.dto.request.BidProposeRequest;
import com.ssafy.fullerting.bid_log.model.entity.BidLog;
import com.ssafy.fullerting.bid_log.repository.BidRepository;
import com.ssafy.fullerting.deal.exception.DealErrorCode;
import com.ssafy.fullerting.deal.exception.DealException;
import com.ssafy.fullerting.deal.model.dto.request.DealProposeRequest;
import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.deal.repository.DealRepository;
import com.ssafy.fullerting.exArticle.exception.ExArticleErrorCode;
import com.ssafy.fullerting.exArticle.exception.ExArticleException;
import com.ssafy.fullerting.exArticle.model.dto.request.ExArticleRegisterRequest;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.repository.ExArticleRepository;
import com.ssafy.fullerting.global.utils.MessageUtils;
import com.ssafy.fullerting.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BidService {
    private final BidRepository bidRepository;
    private final DealRepository dealRepository;
    private final ExArticleRepository exArticleRepository;


    public void deal(BidProposeRequest bidProposeRequest, User user, Long ex_article_id) {
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

}
