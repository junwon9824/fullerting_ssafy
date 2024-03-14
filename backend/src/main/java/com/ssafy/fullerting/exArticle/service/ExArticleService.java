package com.ssafy.fullerting.exArticle.service;

import com.ssafy.fullerting.exArticle.model.dto.request.ExArticleRegisterRequest;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.repository.ExArticleRepository;
import com.ssafy.fullerting.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExArticleService {
    private final ExArticleRepository exArticleRepository;

    public void register(ExArticleRegisterRequest exArticleRegisterRequest, User user) {
        ExArticle exArticle = ExArticle.builder()
                .title(exArticleRegisterRequest.getExArticleTitle())
                .content(exArticleRegisterRequest.getExArticleContent())
                .place(exArticleRegisterRequest.getExArticlePlace())
                .type(exArticleRegisterRequest.getExArticlePayment())
                .user(user)
                .build();

        exArticleRepository.save(exArticle);
    }
}
