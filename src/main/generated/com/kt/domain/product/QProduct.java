package com.kt.domain.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 1209574814L;

    public static final QProduct product = new QProduct("product");

    public final com.kt.common.QBaseEntity _super = new com.kt.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    public final ListPath<com.kt.domain.orderproduct.OrderProduct, com.kt.domain.orderproduct.QOrderProduct> orderProducts = this.<com.kt.domain.orderproduct.OrderProduct, com.kt.domain.orderproduct.QOrderProduct>createList("orderProducts", com.kt.domain.orderproduct.OrderProduct.class, com.kt.domain.orderproduct.QOrderProduct.class, PathInits.DIRECT2);

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final EnumPath<ProductStatus> status = createEnum("status", ProductStatus.class);

    public final NumberPath<Long> stock = createNumber("stock", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QProduct(String variable) {
        super(Product.class, forVariable(variable));
    }

    public QProduct(Path<? extends Product> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProduct(PathMetadata metadata) {
        super(Product.class, metadata);
    }

}

