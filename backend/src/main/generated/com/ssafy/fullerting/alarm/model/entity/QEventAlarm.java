package com.ssafy.fullerting.alarm.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEventAlarm is a Querydsl query type for EventAlarm
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEventAlarm extends EntityPathBase<EventAlarm> {

    private static final long serialVersionUID = -1754924445L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEventAlarm eventAlarm = new QEventAlarm("eventAlarm");

    public final NumberPath<Long> alarmId = createNumber("alarmId", Long.class);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final BooleanPath isChecked = createBoolean("isChecked");

    public final com.ssafy.fullerting.user.model.entity.QMemberProfile receiveUser;

    public final StringPath redirect = createString("redirect");

    public final com.ssafy.fullerting.user.model.entity.QMemberProfile sendUser;

    public final EnumPath<com.ssafy.fullerting.alarm.model.EventAlarmType> type = createEnum("type", com.ssafy.fullerting.alarm.model.EventAlarmType.class);

    public QEventAlarm(String variable) {
        this(EventAlarm.class, forVariable(variable), INITS);
    }

    public QEventAlarm(Path<? extends EventAlarm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEventAlarm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEventAlarm(PathMetadata metadata, PathInits inits) {
        this(EventAlarm.class, metadata, inits);
    }

    public QEventAlarm(Class<? extends EventAlarm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.receiveUser = inits.isInitialized("receiveUser") ? new com.ssafy.fullerting.user.model.entity.QMemberProfile(forProperty("receiveUser")) : null;
        this.sendUser = inits.isInitialized("sendUser") ? new com.ssafy.fullerting.user.model.entity.QMemberProfile(forProperty("sendUser")) : null;
    }

}

