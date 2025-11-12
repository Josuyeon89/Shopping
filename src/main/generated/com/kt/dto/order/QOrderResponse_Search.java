package com.kt.dto.order;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.kt.dto.order.QOrderResponse_Search is a Querydsl Projection type for Search
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QOrderResponse_Search extends ConstructorExpression<OrderResponse.Search> {

    private static final long serialVersionUID = -802741490L;

    public QOrderResponse_Search(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> receiverName, com.querydsl.core.types.Expression<String> ProductName, com.querydsl.core.types.Expression<Long> quantity, com.querydsl.core.types.Expression<Long> totalPrice, com.querydsl.core.types.Expression<com.kt.domain.order.OrderStatus> status, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdAt) {
        super(OrderResponse.Search.class, new Class<?>[]{long.class, String.class, String.class, long.class, long.class, com.kt.domain.order.OrderStatus.class, java.time.LocalDateTime.class}, id, receiverName, ProductName, quantity, totalPrice, status, createdAt);
    }

}

