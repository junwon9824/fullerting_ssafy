package com.ssafy.fullerting.bidLog.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBidLog is a Querydsl query type for BidLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBidLog extends EntityPathBase<BidLog> {

    private static final long serialVersionUID = 679844707L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBidLog bidLog = new QBidLog("bidLog");

    public final NumberPath<Integer> bidLogPrice = createNumber("bidLogPrice", Integer.class);

    public final com.ssafy.fullerting.deal.model.entity.QDeal deal;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> localDateTime = createDateTime("localDateTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QBidLog(String variable) {
        this(BidLog.class, forVariable(variable), INITS);
    }

    public QBidLog(Path<? extends BidLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBidLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBidLog(PathMetadata metadata, PathInits inits) {
        this(BidLog.class, metadata, inits);
    }

    public QBidLog(Class<? extends BidLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.deal = inits.isInitialized("deal") ? new com.ssafy.fullerting.deal.model.entity.QDeal(forProperty("deal"), inits.get("deal")) : null;
    }

}

