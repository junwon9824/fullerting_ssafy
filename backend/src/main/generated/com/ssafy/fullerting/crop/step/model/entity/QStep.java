package com.ssafy.fullerting.crop.step.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStep is a Querydsl query type for Step
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStep extends EntityPathBase<Step> {

    private static final long serialVersionUID = -435488335L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStep step1 = new QStep("step1");

    public final com.ssafy.fullerting.crop.type.model.entity.QCrop crop;

    public final NumberPath<Integer> harvestDay = createNumber("harvestDay", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> step = createNumber("step", Integer.class);

    public QStep(String variable) {
        this(Step.class, forVariable(variable), INITS);
    }

    public QStep(Path<? extends Step> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStep(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStep(PathMetadata metadata, PathInits inits) {
        this(Step.class, metadata, inits);
    }

    public QStep(Class<? extends Step> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.crop = inits.isInitialized("crop") ? new com.ssafy.fullerting.crop.type.model.entity.QCrop(forProperty("crop")) : null;
    }

}

