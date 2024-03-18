package com.ssafy.fullerting.exArticle.service;

import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.deal.repository.DealRepository;
import com.ssafy.fullerting.deal.service.DealService;
import com.ssafy.fullerting.exArticle.model.dto.request.ExArticleRegisterRequest;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType;
import com.ssafy.fullerting.exArticle.repository.ExArticleRepository;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExArticleService {
    private final ExArticleRepository exArticleRepository;
    private final DealRepository dealRepository;
    private final DealService dealService;

    public void register(ExArticleRegisterRequest exArticleRegisterRequest, CustomUser user) {

        LocalDateTime createdAt = LocalDateTime.now(); // 현재 시각 설정

        ExArticle exArticle = ExArticle.builder()
                .title(exArticleRegisterRequest.getExArticleTitle())
                .content(exArticleRegisterRequest.getExArticleContent())
                .place(exArticleRegisterRequest.getExArticlePlace())
                .type(exArticleRegisterRequest.getExArticleType())
                .created_at(createdAt)
                .location(exArticleRegisterRequest.getEx_article_location())
                .user(user)
                .build();

//        exArticleRepository.saveAndFlush(exArticle);
        ExArticle exArticle1 = exArticleRepository.save(exArticle);

        if (exArticleRegisterRequest.getExArticleType().equals(ExArticleType.DEAL)) {
            Deal deal = Deal.builder()
                    .deal_cur_price(exArticleRegisterRequest.getDeal_cur_price())
                    .build();

            deal.setexarticle(exArticle);

            System.out.println("exarttttt   " + exArticle.toString());

            dealRepository.save(deal);

            exArticle1.setdeal(deal);
//            System.out.println("exarttttt22222      " + exArticle.toString());

        }


    }


    public List<ExArticle> allArticle() {
        List<ExArticle> exArticle = exArticleRepository.findAll();
        return exArticle;
    }
}
