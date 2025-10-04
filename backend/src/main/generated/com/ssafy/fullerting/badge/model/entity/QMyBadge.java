package com.ssafy.fullerting.badge.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMyBadge is a Querydsl query type for MyBadge
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMyBadge extends EntityPathBase<MyBadge> {

    private static final long serialVersionUID = 403712057L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMyBadge myBadge = new QMyBadge("myBadge");

    public final QBadge badge;

    public final com.ssafy.fullerting.user.model.entity.QMemberProfile customUser;

    public final NumberPath<Long> myBadgeId = createNumber("myBadgeId", Long.class);

    public QMyBadge(String variable) {
        this(MyBadge.class, forVariable(variable), INITS);
    }

    public QMyBadge(Path<? extends MyBadge> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMyBadge(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMyBadge(PathMetadata metadata, PathInits inits) {
        this(MyBadge.class, metadata, inits);
    }

    public QMyBadge(Class<? extends MyBadge> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.badge = inits.isInitialized("badge") ? new QBadge(forProperty("badge")) : null;
        this.customUser = inits.isInitialized("customUser") ? new com.ssafy.fullerting.user.model.entity.QMemberProfile(forProperty("customUser")) : null;
    }

}

