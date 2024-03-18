package com.ssafy.fullerting.exArticle.service;

import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.deal.repository.DealRepository;
import com.ssafy.fullerting.deal.service.DealService;
import com.ssafy.fullerting.exArticle.model.dto.request.ExArticleRegisterRequest;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType;
import com.ssafy.fullerting.exArticle.repository.ExArticleRepository;
import com.ssafy.fullerting.user.exception.UserErrorCode;
import com.ssafy.fullerting.user.exception.UserException;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import com.ssafy.fullerting.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExArticleService {
    private final ExArticleRepository exArticleRepository;
    private final DealRepository dealRepository;
    private final UserRepository userRepository;
    private final DealService dealService;

    public void register(ExArticleRegisterRequest exArticleRegisterRequest, String email1) {

        CustomUser customUser = userRepository.findByEmail(email1).orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER));
//        log.info("ussssss"+customUser.getEmail());
        log.info("ussssss" + email1);


        LocalDateTime createdAt = LocalDateTime.now(); // 현재 시각 설정

        ExArticle exArticle = ExArticle.builder()
                .id(exArticleRegisterRequest.getId())
                .title(exArticleRegisterRequest.getExArticleTitle())
                .content(exArticleRegisterRequest.getExArticleContent())
                .place(exArticleRegisterRequest.getExArticlePlace())
                .type(exArticleRegisterRequest.getExArticleType())
                .created_at(createdAt)
                .location(exArticleRegisterRequest.getEx_article_location())
                .user(customUser)
                .favorite(exArticleRegisterRequest.getFavorite())
                .build();

            log.info("exxxxx"+exArticle.toString());
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
