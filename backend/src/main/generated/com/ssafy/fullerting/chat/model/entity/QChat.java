package com.ssafy.fullerting.chat.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChat is a Querydsl query type for Chat
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChat extends EntityPathBase<Chat> {

    private static final long serialVersionUID = 524365829L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChat chat = new QChat("chat");

    public final QChatRoom chatRoom;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath message = createString("message");

    public final DateTimePath<java.sql.Timestamp> sendAt = createDateTime("sendAt", java.sql.Timestamp.class);

    public final com.ssafy.fullerting.user.model.entity.QMemberProfile sender;

    public QChat(String variable) {
        this(Chat.class, forVariable(variable), INITS);
    }

    public QChat(Path<? extends Chat> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChat(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChat(PathMetadata metadata, PathInits inits) {
        this(Chat.class, metadata, inits);
    }

    public QChat(Class<? extends Chat> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatRoom = inits.isInitialized("chatRoom") ? new QChatRoom(forProperty("chatRoom"), inits.get("chatRoom")) : null;
        this.sender = inits.isInitialized("sender") ? new com.ssafy.fullerting.user.model.entity.QMemberProfile(forProperty("sender")) : null;
    }

}

