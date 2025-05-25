package com.ssafy.fullerting.user.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberProfile is a Querydsl query type for MemberProfile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberProfile extends EntityPathBase<MemberProfile> {

    private static final long serialVersionUID = 1636803407L;

    public static final QMemberProfile memberProfile = new QMemberProfile("memberProfile");

    public final StringPath authProvider = createString("authProvider");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath location = createString("location");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath rank = createString("rank");

    public final StringPath role = createString("role");

    public final StringPath thumbnail = createString("thumbnail");

    public QMemberProfile(String variable) {
        super(MemberProfile.class, forVariable(variable));
    }

    public QMemberProfile(Path<? extends MemberProfile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberProfile(PathMetadata metadata) {
        super(MemberProfile.class, metadata);
    }

}

