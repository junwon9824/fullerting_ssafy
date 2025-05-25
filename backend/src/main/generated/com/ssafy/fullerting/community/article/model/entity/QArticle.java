package com.ssafy.fullerting.community.article.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArticle is a Querydsl query type for Article
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArticle extends EntityPathBase<Article> {

    private static final long serialVersionUID = -2022302518L;

    public static final QArticle article = new QArticle("article");

    public final ListPath<com.ssafy.fullerting.community.comment.model.entity.Comment, com.ssafy.fullerting.community.comment.model.entity.QComment> comments = this.<com.ssafy.fullerting.community.comment.model.entity.Comment, com.ssafy.fullerting.community.comment.model.entity.QComment>createList("comments", com.ssafy.fullerting.community.comment.model.entity.Comment.class, com.ssafy.fullerting.community.comment.model.entity.QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.ssafy.fullerting.image.model.entity.Image, com.ssafy.fullerting.image.model.entity.QImage> images = this.<com.ssafy.fullerting.image.model.entity.Image, com.ssafy.fullerting.image.model.entity.QImage>createList("images", com.ssafy.fullerting.image.model.entity.Image.class, com.ssafy.fullerting.image.model.entity.QImage.class, PathInits.DIRECT2);

    public final NumberPath<Integer> love = createNumber("love", Integer.class);

    public final ListPath<com.ssafy.fullerting.community.love.model.entity.Love, com.ssafy.fullerting.community.love.model.entity.QLove> loves = this.<com.ssafy.fullerting.community.love.model.entity.Love, com.ssafy.fullerting.community.love.model.entity.QLove>createList("loves", com.ssafy.fullerting.community.love.model.entity.Love.class, com.ssafy.fullerting.community.love.model.entity.QLove.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final EnumPath<com.ssafy.fullerting.community.article.model.enums.ArticleType> type = createEnum("type", com.ssafy.fullerting.community.article.model.enums.ArticleType.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QArticle(String variable) {
        super(Article.class, forVariable(variable));
    }

    public QArticle(Path<? extends Article> path) {
        super(path.getType(), path.getMetadata());
    }

    public QArticle(PathMetadata metadata) {
        super(Article.class, metadata);
    }

}

