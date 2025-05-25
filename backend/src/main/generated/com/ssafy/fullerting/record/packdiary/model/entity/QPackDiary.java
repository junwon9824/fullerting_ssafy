package com.ssafy.fullerting.record.packdiary.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPackDiary is a Querydsl query type for PackDiary
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPackDiary extends EntityPathBase<PackDiary> {

    private static final long serialVersionUID = 1034143720L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPackDiary packDiary = new QPackDiary("packDiary");

    public final DateTimePath<java.sql.Timestamp> createdAt = createDateTime("createdAt", java.sql.Timestamp.class);

    public final com.ssafy.fullerting.crop.type.model.entity.QCrop crop;

    public final DatePath<java.time.LocalDate> culEndAt = createDate("culEndAt", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> culStartAt = createDate("culStartAt", java.time.LocalDate.class);

    public final NumberPath<Integer> growthStep = createNumber("growthStep", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath title = createString("title");

    public final com.ssafy.fullerting.user.model.entity.QMemberProfile user;

    public QPackDiary(String variable) {
        this(PackDiary.class, forVariable(variable), INITS);
    }

    public QPackDiary(Path<? extends PackDiary> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPackDiary(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPackDiary(PathMetadata metadata, PathInits inits) {
        this(PackDiary.class, metadata, inits);
    }

    public QPackDiary(Class<? extends PackDiary> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.crop = inits.isInitialized("crop") ? new com.ssafy.fullerting.crop.type.model.entity.QCrop(forProperty("crop")) : null;
        this.user = inits.isInitialized("user") ? new com.ssafy.fullerting.user.model.entity.QMemberProfile(forProperty("user")) : null;
    }

}

