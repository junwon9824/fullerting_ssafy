package com.ssafy.fullerting.user.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCustomUser is a Querydsl query type for CustomUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomUser extends EntityPathBase<CustomUser> {

    private static final long serialVersionUID = 456155452L;

    public static final QCustomUser customUser = new QCustomUser("customUser");

    public final StringPath authProvider = createString("authProvider");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath location = createString("location");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath rank = createString("rank");

    public final StringPath role = createString("role");

    public final StringPath thumbnail = createString("thumbnail");

    public QCustomUser(String variable) {
        super(CustomUser.class, forVariable(variable));
    }

    public QCustomUser(Path<? extends CustomUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCustomUser(PathMetadata metadata) {
        super(CustomUser.class, metadata);
    }

}

