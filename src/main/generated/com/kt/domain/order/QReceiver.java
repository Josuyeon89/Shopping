package com.kt.domain.order;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReceiver is a Querydsl query type for Receiver
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QReceiver extends BeanPath<Receiver> {

    private static final long serialVersionUID = 1504164897L;

    public static final QReceiver receiver = new QReceiver("receiver");

    public final StringPath address = createString("address");

    public final StringPath mobile = createString("mobile");

    public final StringPath name = createString("name");

    public QReceiver(String variable) {
        super(Receiver.class, forVariable(variable));
    }

    public QReceiver(Path<? extends Receiver> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReceiver(PathMetadata metadata) {
        super(Receiver.class, metadata);
    }

}

