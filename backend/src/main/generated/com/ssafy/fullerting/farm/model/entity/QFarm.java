package com.ssafy.fullerting.farm.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFarm is a Querydsl query type for Farm
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFarm extends EntityPathBase<Farm> {

    private static final long serialVersionUID = 482049601L;

    public static final QFarm farm = new QFarm("farm");

    public final StringPath address = createString("address");

    public final NumberPath<Integer> areaLcd = createNumber("areaLcd", Integer.class);

    public final StringPath areaLnm = createString("areaLnm");

    public final NumberPath<Integer> areaMcd = createNumber("areaMcd", Integer.class);

    public final StringPath areaMnm = createString("areaMnm");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath offSite = createString("offSite");

    public final NumberPath<Float> posLat = createNumber("posLat", Float.class);

    public final NumberPath<Float> posLng = createNumber("posLng", Float.class);

    public final StringPath type = createString("type");

    public QFarm(String variable) {
        super(Farm.class, forVariable(variable));
    }

    public QFarm(Path<? extends Farm> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFarm(PathMetadata metadata) {
        super(Farm.class, metadata);
    }

}

