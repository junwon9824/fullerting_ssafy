package com.ssafy.fullerting.deal.service;

import com.ssafy.fullerting.deal.model.dto.request.DealProposeRequest;
import com.ssafy.fullerting.deal.repository.DealRepository;
import com.ssafy.fullerting.exArticle.model.dto.request.ExArticleRegisterRequest;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.repository.ExArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DealService {
    private final DealRepository dealRepository;

    public void register(ExArticleRegisterRequest exArticleRegisterRequest) {


    }

    public void deal(DealProposeRequest dealProposeRequest) {

    }
}
