package com.ssafy.fullerting.crop.tip.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTip is a Querydsl query type for Tip
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTip extends EntityPathBase<Tip> {

    private static final long serialVersionUID = -584872311L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTip tip = new QTip("tip");

    public final StringPath content = createString("content");

    public final com.ssafy.fullerting.crop.type.model.entity.QCrop crop;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> step = createNumber("step", Integer.class);

    public QTip(String variable) {
        this(Tip.class, forVariable(variable), INITS);
    }

    public QTip(Path<? extends Tip> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTip(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTip(PathMetadata metadata, PathInits inits) {
        this(Tip.class, metadata, inits);
    }

    public QTip(Class<? extends Tip> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.crop = inits.isInitialized("crop") ? new com.ssafy.fullerting.crop.type.model.entity.QCrop(forProperty("crop")) : null;
    }

}

