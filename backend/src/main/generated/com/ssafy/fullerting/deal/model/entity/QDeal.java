package com.ssafy.fullerting.deal.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeal is a Querydsl query type for Deal
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeal extends EntityPathBase<Deal> {

    private static final long serialVersionUID = 313091373L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDeal deal = new QDeal("deal");

    public final NumberPath<Integer> dealCurPrice = createNumber("dealCurPrice", Integer.class);

    public final com.ssafy.fullerting.exArticle.model.entity.QExArticle exArticle;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QDeal(String variable) {
        this(Deal.class, forVariable(variable), INITS);
    }

    public QDeal(Path<? extends Deal> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDeal(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDeal(PathMetadata metadata, PathInits inits) {
        this(Deal.class, metadata, inits);
    }

    public QDeal(Class<? extends Deal> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.exArticle = inits.isInitialized("exArticle") ? new com.ssafy.fullerting.exArticle.model.entity.QExArticle(forProperty("exArticle"), inits.get("exArticle")) : null;
    }

}

