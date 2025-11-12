package com.kt.domain.orderproduct;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderProduct is a Querydsl query type for OrderProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderProduct extends EntityPathBase<OrderProduct> {

    private static final long serialVersionUID = -2112551396L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderProduct orderProduct = new QOrderProduct("orderProduct");

    public final com.kt.common.QBaseEntity _super = new com.kt.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final com.kt.domain.order.QOrder order;

    public final com.kt.domain.product.QProduct product;

    public final NumberPath<Long> quantity = createNumber("quantity", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QOrderProduct(String variable) {
        this(OrderProduct.class, forVariable(variable), INITS);
    }

    public QOrderProduct(Path<? extends OrderProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderProduct(PathMetadata metadata, PathInits inits) {
        this(OrderProduct.class, metadata, inits);
    }

    public QOrderProduct(Class<? extends OrderProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.order = inits.isInitialized("order") ? new com.kt.domain.order.QOrder(forProperty("order"), inits.get("order")) : null;
        this.product = inits.isInitialized("product") ? new com.kt.domain.product.QProduct(forProperty("product")) : null;
    }

}

