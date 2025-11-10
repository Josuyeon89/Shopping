package com.kt.domain.payment;

import com.kt.common.BaseEntity;
import com.kt.domain.order.Order;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Payment extends BaseEntity {
    private Long totalPrice;
    private Long deliveryFee;
    @Enumerated(EnumType.STRING)
    private PaymentType type;

    @OneToOne
    private Order order;

}
