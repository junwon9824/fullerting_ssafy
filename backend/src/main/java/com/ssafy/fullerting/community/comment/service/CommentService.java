package com.ssafy.fullerting.community.comment.service;

import com.ssafy.fullerting.community.article.exception.ArticleErrorCode;
import com.ssafy.fullerting.community.article.exception.ArticleException;
import com.ssafy.fullerting.community.article.model.entity.Article;
import com.ssafy.fullerting.community.article.repository.ArticleRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final ArticleRepository articleRepository;

    public CommentService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void registcomment(Long article_id) {
        Article article = articleRepository.findById(article_id).orElseThrow(() -> new
                ArticleException(ArticleErrorCode.NOT_EXISTS));
    }


}
