package com.ssafy.fullerting.community.article.service;

import com.ssafy.fullerting.community.article.exception.ArticleErrorCode;
import com.ssafy.fullerting.community.article.exception.ArticleException;
import com.ssafy.fullerting.community.article.model.dto.request.RegistArticleRequest;
import com.ssafy.fullerting.community.article.model.dto.response.ArticleResponse;
import com.ssafy.fullerting.community.article.model.entity.Article;
import com.ssafy.fullerting.community.article.model.enums.ArticleType;
import com.ssafy.fullerting.community.article.repository.ArticleRepository;
import com.ssafy.fullerting.user.model.dto.response.UserResponse;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import com.ssafy.fullerting.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserService userService;

    public void registarticle(RegistArticleRequest registArticleRequest) {

        UserResponse userResponse = userService.getUserInfo();
        CustomUser customUser = userResponse.toEntity(userResponse);
        log.info("type" + registArticleRequest.getType());

        articleRepository.save(Article.builder()
                .type(registArticleRequest.getType())
                .content(registArticleRequest.getContent())
                .createdAt(LocalDateTime.now())
                .title(registArticleRequest.getTitle())
                .love(0)
                .userId(customUser.getId())
                .build());

    }

    public void update(RegistArticleRequest registArticleRequest, Long articleId) {
        UserResponse userResponse = userService.getUserInfo();
        CustomUser customUser = userResponse.toEntity(userResponse);


        Article article = articleRepository.findById(articleId).orElseThrow(() -> new
                ArticleException(ArticleErrorCode.NOT_EXISTS));

        if (article.getUserId() != customUser.getId()) {
            throw new ArticleException(ArticleErrorCode.NOT_MINE);
        }

        article.setContent(registArticleRequest.getContent());
        article.setType(registArticleRequest.getType());
        article.setTitle(registArticleRequest.getTitle());
        articleRepository.save(article);

    }

    public ArticleResponse findarticlebyid(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() ->
                new ArticleException(ArticleErrorCode.NOT_EXISTS));
        return article.toResponse();

    }

    public void deletearticlebyid(Long articleId) {

        UserResponse userResponse = userService.getUserInfo();
        CustomUser customUser = userResponse.toEntity(userResponse);

        Article article = articleRepository.findById(articleId).orElseThrow(() ->
                new ArticleException(ArticleErrorCode.NOT_EXISTS));

        if (article.getUserId() != customUser.getId()) {
            throw new ArticleException(ArticleErrorCode.NOT_MINE);
        }

        articleRepository.delete(article);
    }

    public List<ArticleResponse> findAllArticle() {
        return articleRepository.findAll().stream().map(article -> {
                    ArticleResponse articleResponse = article.toResponse();
                    return articleResponse;
                })
                .collect(Collectors.toList());

    }

    public List<ArticleResponse> findAllArticlebyCategory(String keyword) {


        if (keyword.equals("FREE_BOARD")) {
            return articleRepository.findAllByType((ArticleType.FREE_BOARD)).stream().map(article -> {
                        ArticleResponse articleResponse = article.toResponse();
                        return articleResponse;
                    })
                    .collect(Collectors.toList());
        } else if (keyword.equals("INTRODUCE")) {
            return articleRepository.findAllByType((ArticleType.INTRODUCE)).stream().map(article -> {
                        ArticleResponse articleResponse = article.toResponse();
                        return articleResponse;
                    })
                    .collect(Collectors.toList());
        } else if (keyword.equals("COOK")) {
            return articleRepository.findAllByType((ArticleType.COOK)).stream().map(article -> {
                        ArticleResponse articleResponse = article.toResponse();
                        return articleResponse;
                    })
                    .collect(Collectors.toList());
        } else {
            return articleRepository.findAllByType((ArticleType.SHARE_TIPS)).stream().map(article -> {
                        ArticleResponse articleResponse = article.toResponse();
                        return articleResponse;
                    })
                    .collect(Collectors.toList());
        }


    }

    public List<ArticleResponse> search(String keyword) {

        return articleRepository.
                findAllByContentandtitle(keyword)
                .stream().map(article -> {
                    ArticleResponse articleResponse = article.toResponse();
                    return articleResponse;
                })
                .collect(Collectors.toList());
    }
}
