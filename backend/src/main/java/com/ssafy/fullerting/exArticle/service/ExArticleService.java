package com.ssafy.fullerting.exArticle.service;

import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.deal.repository.DealRepository;
import com.ssafy.fullerting.deal.service.DealService;
import com.ssafy.fullerting.exArticle.exception.ExArticleErrorCode;
import com.ssafy.fullerting.exArticle.exception.ExArticleException;
import com.ssafy.fullerting.exArticle.model.dto.request.ExArticleRegisterRequest;
import com.ssafy.fullerting.exArticle.model.dto.response.ExArticleResponse;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType;
import com.ssafy.fullerting.exArticle.repository.ExArticleRepository;
import com.ssafy.fullerting.favorite.model.entity.Favorite;
import com.ssafy.fullerting.favorite.repository.favoriteRepository;
import com.ssafy.fullerting.user.exception.UserErrorCode;
import com.ssafy.fullerting.user.exception.UserException;
import com.ssafy.fullerting.user.model.dto.response.UserResponse;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import com.ssafy.fullerting.user.repository.UserRepository;
import com.ssafy.fullerting.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExArticleService {
    private final ExArticleRepository exArticleRepository;
    private final DealRepository dealRepository;
    private final UserRepository userRepository;
    private final favoriteRepository favoriteRepository;
    private final DealService dealService;
    private final UserService userService;


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

        log.info("exxxxx" + exArticle.toString());
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

    public List<ExArticleResponse> allArticle() {
        List<ExArticle> exArticle = exArticleRepository.findAll();
        List<ExArticleResponse> exArticleResponses = exArticle.stream().map(ExArticle::toResponse).collect(Collectors.toList());

        return exArticleResponses;
    }


    public void like(Long ex_article_id) {
        //좋아요 로직
        ExArticle article = exArticleRepository.findById(ex_article_id).orElseThrow(() ->
                new ExArticleException(ExArticleErrorCode.NOT_EXISTS));


        UserResponse userResponse = userService.getUserInfo();
        Long userid = userResponse.getId();
        Long articleid = article.getId();

        Optional<Favorite> favorite1 = favoriteRepository.findByUserIdAndExArticleId(userid, articleid);

        if (favorite1.isPresent()) {
            throw new ExArticleException(ExArticleErrorCode.ALREADY_LIKED);
        }

        Favorite favorite = new Favorite();
        favorite.setExArticle(article);
        favorite.setUser(userResponse.toEntity(userResponse));

        article.addfavorite(favorite);

        favoriteRepository.save(favorite);
    }

    public List<ExArticleResponse> keyword(String keyword) { //keyword 검색.
        List<ExArticle> exArticles = exArticleRepository.findAllByTitleContaining(keyword).orElseThrow(() ->
                new ExArticleException(ExArticleErrorCode.NOT_EXISTS));

        return exArticles.stream().map(ExArticle::toResponse).collect(Collectors.toList());
    }

}
