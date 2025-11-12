package com.kt.repository.order;

import com.kt.domain.order.QOrder;
import com.kt.domain.orderproduct.QOrderProduct;
import com.kt.domain.product.QProduct;
import com.kt.dto.order.QOrderResponse_Search;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.kt.dto.order.OrderResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {
    // QClass import해서 쿼리 작성할 때 사용
    private final QOrder order = QOrder.order;
    private final QOrderProduct orderProduct = QOrderProduct.orderProduct;
    private final QProduct product = QProduct.product;
    private final JPAQueryFactory jPAQueryFactory;

    public Page<OrderResponse.Search> search (String keyword, Pageable pageable) {
        // 페이징 구현할 때 offset, limit
        // select *
        //booleanBuilder, BooleanExpression
        var booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(containsProductName(keyword));

        var content = jPAQueryFactory
                .select(new QOrderResponse_Search(
                        order.id,
                        order.receiver.name,
                        product.name,
                        orderProduct.quantity,
                        product.price.multiply(orderProduct.quantity),
                        order.status,
                        order.createdAt
                ))
                .from(order)
                .join(orderProduct).on(orderProduct.id.eq(order.id))
                .join(product).on(orderProduct.id.eq(product.id))
                .where(booleanBuilder)
                .orderBy(order.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        var total = (long)jPAQueryFactory.select(order.id)
                .from(order)
                .join(orderProduct).on(orderProduct.order.id.eq(order.id))
                .join(product).on(orderProduct.product.id.eq(product.id))
                .where(booleanBuilder)
                .fetch().size();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression containsProductName (String keyword) {
        return Strings.isNotBlank(keyword) ? product.name.containsIgnoreCase(keyword) : null;
    }


}
