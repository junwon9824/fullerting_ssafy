package com.ssafy.fullerting.trans.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTrans is a Querydsl query type for Trans
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTrans extends EntityPathBase<Trans> {

    private static final long serialVersionUID = 969120485L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTrans trans = new QTrans("trans");

    public final com.ssafy.fullerting.exArticle.model.entity.QExArticle exArticle;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> trans_sell_price = createNumber("trans_sell_price", Integer.class);

    public QTrans(String variable) {
        this(Trans.class, forVariable(variable), INITS);
    }

    public QTrans(Path<? extends Trans> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTrans(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTrans(PathMetadata metadata, PathInits inits) {
        this(Trans.class, metadata, inits);
    }

    public QTrans(Class<? extends Trans> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.exArticle = inits.isInitialized("exArticle") ? new com.ssafy.fullerting.exArticle.model.entity.QExArticle(forProperty("exArticle"), inits.get("exArticle")) : null;
    }

}

