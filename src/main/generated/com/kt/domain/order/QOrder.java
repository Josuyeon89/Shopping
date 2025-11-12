package com.kt.domain.order;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = -797429156L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrder order = new QOrder("order1");

    public final com.kt.common.QBaseEntity _super = new com.kt.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> deliveredAt = createDateTime("deliveredAt", java.time.LocalDateTime.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final ListPath<com.kt.domain.orderproduct.OrderProduct, com.kt.domain.orderproduct.QOrderProduct> orderProducts = this.<com.kt.domain.orderproduct.OrderProduct, com.kt.domain.orderproduct.QOrderProduct>createList("orderProducts", com.kt.domain.orderproduct.OrderProduct.class, com.kt.domain.orderproduct.QOrderProduct.class, PathInits.DIRECT2);

    public final QReceiver receiver;

    public final EnumPath<OrderStatus> status = createEnum("status", OrderStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.kt.domain.user.QUser user;

    public QOrder(String variable) {
        this(Order.class, forVariable(variable), INITS);
    }

    public QOrder(Path<? extends Order> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrder(PathMetadata metadata, PathInits inits) {
        this(Order.class, metadata, inits);
    }

    public QOrder(Class<? extends Order> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.receiver = inits.isInitialized("receiver") ? new QReceiver(forProperty("receiver")) : null;
        this.user = inits.isInitialized("user") ? new com.kt.domain.user.QUser(forProperty("user")) : null;
    }

}

