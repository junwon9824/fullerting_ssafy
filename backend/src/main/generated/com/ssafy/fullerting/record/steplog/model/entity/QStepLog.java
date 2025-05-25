package com.ssafy.fullerting.record.steplog.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStepLog is a Querydsl query type for StepLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStepLog extends EntityPathBase<StepLog> {

    private static final long serialVersionUID = -1348597720L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStepLog stepLog = new QStepLog("stepLog");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.ssafy.fullerting.record.packdiary.model.entity.QPackDiary packDiary;

    public final com.ssafy.fullerting.crop.step.model.entity.QStep step;

    public final DateTimePath<java.sql.Timestamp> updatedAt = createDateTime("updatedAt", java.sql.Timestamp.class);

    public QStepLog(String variable) {
        this(StepLog.class, forVariable(variable), INITS);
    }

    public QStepLog(Path<? extends StepLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStepLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStepLog(PathMetadata metadata, PathInits inits) {
        this(StepLog.class, metadata, inits);
    }

    public QStepLog(Class<? extends StepLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.packDiary = inits.isInitialized("packDiary") ? new com.ssafy.fullerting.record.packdiary.model.entity.QPackDiary(forProperty("packDiary"), inits.get("packDiary")) : null;
        this.step = inits.isInitialized("step") ? new com.ssafy.fullerting.crop.step.model.entity.QStep(forProperty("step"), inits.get("step")) : null;
    }

}

