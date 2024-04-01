package com.ssafy.fullerting.community.love.service;

import com.ssafy.fullerting.community.article.exception.ArticleErrorCode;
import com.ssafy.fullerting.community.article.exception.ArticleException;
import com.ssafy.fullerting.community.article.model.entity.Article;
import com.ssafy.fullerting.community.article.repository.ArticleRepository;
import com.ssafy.fullerting.community.love.model.entity.Love;
import com.ssafy.fullerting.community.love.repository.LoveRepository;
import com.ssafy.fullerting.user.model.dto.response.UserResponse;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import com.ssafy.fullerting.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoveService {
    private final ArticleRepository articleRepository;
    private final LoveRepository loveRepository;
    private final UserService userService;

    @Transactional
    public void like(Long articleId) {

        Article article = articleRepository.findById(articleId).
                orElseThrow(() -> new ArticleException(ArticleErrorCode.NOT_EXISTS));
        Hibernate.initialize(article.getLoves());

        log.info("article"+article.getLoves());
        UserResponse userResponse = userService.getUserInfo();
        CustomUser customUser = userResponse.toEntity(userResponse);
        AtomicBoolean islike = new AtomicBoolean(false);

        Iterator<Love> iterator = article.getLoves().iterator();

        while (iterator.hasNext()) {
            Love love = iterator.next();
            if (love.getCustomUser().getId() == customUser.getId()) {
                iterator.remove();
                article.removelove(love);

                loveRepository.delete(love);
                article.setLove(article.getLove() - 1);
                islike.set(true);
            }
        }
        log.info("article"+article.getLoves());


//        article.getLoves().forEach(
//                love -> {
//                    if (love.getCustomUser().getId() == customUser.getId()) {
//                        loveRepository.delete(love);
//                        article.setLove(article.getLove() - 1);
//                        article.removelove(love);
//                        islike.set(true);
//                    }
//                }
//        );


        if (!islike.get()) {

            Love love = Love.builder()
                    .article(article)
                    .customUser(customUser)
                    .build();
            article.addlove(love);
            loveRepository.save(love);

            article.setLove(article.getLove() + 1);

            log.info("article"+article.getLoves());

        }

        articleRepository.save(article);


    }
}
