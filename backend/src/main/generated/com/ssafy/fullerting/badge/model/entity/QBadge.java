package com.ssafy.fullerting.badge.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBadge is a Querydsl query type for Badge
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBadge extends EntityPathBase<Badge> {

    private static final long serialVersionUID = 601869061L;

    public static final QBadge badge = new QBadge("badge");

    public final NumberPath<Long> badgeId = createNumber("badgeId", Long.class);

    public final StringPath img = createString("img");

    public final StringPath name = createString("name");

    public final StringPath trigger = createString("trigger");

    public QBadge(String variable) {
        super(Badge.class, forVariable(variable));
    }

    public QBadge(Path<? extends Badge> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBadge(PathMetadata metadata) {
        super(Badge.class, metadata);
    }

}

