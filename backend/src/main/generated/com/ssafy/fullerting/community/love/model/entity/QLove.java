package com.ssafy.fullerting.community.love.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLove is a Querydsl query type for Love
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLove extends EntityPathBase<Love> {

    private static final long serialVersionUID = -529284834L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLove love = new QLove("love");

    public final com.ssafy.fullerting.community.article.model.entity.QArticle article;

    public final com.ssafy.fullerting.user.model.entity.QMemberProfile customUser;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QLove(String variable) {
        this(Love.class, forVariable(variable), INITS);
    }

    public QLove(Path<? extends Love> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLove(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLove(PathMetadata metadata, PathInits inits) {
        this(Love.class, metadata, inits);
    }

    public QLove(Class<? extends Love> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.article = inits.isInitialized("article") ? new com.ssafy.fullerting.community.article.model.entity.QArticle(forProperty("article")) : null;
        this.customUser = inits.isInitialized("customUser") ? new com.ssafy.fullerting.user.model.entity.QMemberProfile(forProperty("customUser")) : null;
    }

}

