package com.kt.dto.order;

import com.kt.domain.order.OrderStatus;
import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;

public interface OrderResponse {
    // queryDSL 결과를 dto에 매핑하는 방법
    // 1 : 클래스 프로젝션 (Search 클래스가 Q 클래스로 만들어지면 new로)
    // 2 : 어노테이션 프로젝션 (@QueryProjection)
    // 3 : 그냥 POJO로 직접 매핑

    record Search (
            Long id,
            String receiverName,
            String ProductName,
            Long quantity,
            Long totalPrice,
            OrderStatus status,
            LocalDateTime createdAt
    ) {
        @QueryProjection
        public Search{}
    }
}
