package com.ssafy.fullerting.favorite.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFavorite is a Querydsl query type for Favorite
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFavorite extends EntityPathBase<Favorite> {

    private static final long serialVersionUID = -378680179L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFavorite favorite = new QFavorite("favorite");

    public final com.ssafy.fullerting.exArticle.model.entity.QExArticle exArticle;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.ssafy.fullerting.user.model.entity.QMemberProfile user;

    public QFavorite(String variable) {
        this(Favorite.class, forVariable(variable), INITS);
    }

    public QFavorite(Path<? extends Favorite> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFavorite(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFavorite(PathMetadata metadata, PathInits inits) {
        this(Favorite.class, metadata, inits);
    }

    public QFavorite(Class<? extends Favorite> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.exArticle = inits.isInitialized("exArticle") ? new com.ssafy.fullerting.exArticle.model.entity.QExArticle(forProperty("exArticle"), inits.get("exArticle")) : null;
        this.user = inits.isInitialized("user") ? new com.ssafy.fullerting.user.model.entity.QMemberProfile(forProperty("user")) : null;
    }

}

