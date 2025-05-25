package com.ssafy.fullerting.exArticle.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExArticle is a Querydsl query type for ExArticle
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExArticle extends EntityPathBase<ExArticle> {

    private static final long serialVersionUID = -1986773115L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExArticle exArticle = new QExArticle("exArticle");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> created_at = createDateTime("created_at", java.time.LocalDateTime.class);

    public final com.ssafy.fullerting.deal.model.entity.QDeal deal;

    public final ListPath<com.ssafy.fullerting.favorite.model.entity.Favorite, com.ssafy.fullerting.favorite.model.entity.QFavorite> favorite = this.<com.ssafy.fullerting.favorite.model.entity.Favorite, com.ssafy.fullerting.favorite.model.entity.QFavorite>createList("favorite", com.ssafy.fullerting.favorite.model.entity.Favorite.class, com.ssafy.fullerting.favorite.model.entity.QFavorite.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.ssafy.fullerting.image.model.entity.Image, com.ssafy.fullerting.image.model.entity.QImage> image = this.<com.ssafy.fullerting.image.model.entity.Image, com.ssafy.fullerting.image.model.entity.QImage>createList("image", com.ssafy.fullerting.image.model.entity.Image.class, com.ssafy.fullerting.image.model.entity.QImage.class, PathInits.DIRECT2);

    public final BooleanPath isDone = createBoolean("isDone");

    public final StringPath location = createString("location");

    public final com.ssafy.fullerting.record.packdiary.model.entity.QPackDiary packDiary;

    public final StringPath place = createString("place");

    public final NumberPath<Long> purchaserId = createNumber("purchaserId", Long.class);

    public final StringPath title = createString("title");

    public final com.ssafy.fullerting.trans.model.entity.QTrans trans;

    public final EnumPath<com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType> type = createEnum("type", com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType.class);

    public final com.ssafy.fullerting.user.model.entity.QMemberProfile user;

    public QExArticle(String variable) {
        this(ExArticle.class, forVariable(variable), INITS);
    }

    public QExArticle(Path<? extends ExArticle> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExArticle(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExArticle(PathMetadata metadata, PathInits inits) {
        this(ExArticle.class, metadata, inits);
    }

    public QExArticle(Class<? extends ExArticle> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.deal = inits.isInitialized("deal") ? new com.ssafy.fullerting.deal.model.entity.QDeal(forProperty("deal"), inits.get("deal")) : null;
        this.packDiary = inits.isInitialized("packDiary") ? new com.ssafy.fullerting.record.packdiary.model.entity.QPackDiary(forProperty("packDiary"), inits.get("packDiary")) : null;
        this.trans = inits.isInitialized("trans") ? new com.ssafy.fullerting.trans.model.entity.QTrans(forProperty("trans"), inits.get("trans")) : null;
        this.user = inits.isInitialized("user") ? new com.ssafy.fullerting.user.model.entity.QMemberProfile(forProperty("user")) : null;
    }

}

