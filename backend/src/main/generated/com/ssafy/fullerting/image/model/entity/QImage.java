package com.ssafy.fullerting.image.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QImage is a Querydsl query type for Image
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QImage extends EntityPathBase<Image> {

    private static final long serialVersionUID = -283852795L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QImage image = new QImage("image");

    public final com.ssafy.fullerting.community.article.model.entity.QArticle article;

    public final com.ssafy.fullerting.record.diary.model.entity.QDiary diary;

    public final com.ssafy.fullerting.exArticle.model.entity.QExArticle exArticle;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgStoreUrl = createString("imgStoreUrl");

    public QImage(String variable) {
        this(Image.class, forVariable(variable), INITS);
    }

    public QImage(Path<? extends Image> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QImage(PathMetadata metadata, PathInits inits) {
        this(Image.class, metadata, inits);
    }

    public QImage(Class<? extends Image> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.article = inits.isInitialized("article") ? new com.ssafy.fullerting.community.article.model.entity.QArticle(forProperty("article")) : null;
        this.diary = inits.isInitialized("diary") ? new com.ssafy.fullerting.record.diary.model.entity.QDiary(forProperty("diary"), inits.get("diary")) : null;
        this.exArticle = inits.isInitialized("exArticle") ? new com.ssafy.fullerting.exArticle.model.entity.QExArticle(forProperty("exArticle"), inits.get("exArticle")) : null;
    }

}

