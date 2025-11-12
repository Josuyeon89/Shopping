package com.kt.service;

import com.kt.common.ErrorCode;
import com.kt.common.Preconditions;
import com.kt.domain.order.Order;
import com.kt.domain.order.Receiver;
import com.kt.domain.orderproduct.OrderProduct;
import com.kt.repository.order.OrderRepository;
import com.kt.repository.orderproduct.OrderProductRepository;
import com.kt.repository.product.ProductRepository;
import com.kt.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;

    public void create (
            Long userId,
            Long productId,
            String receiverName,
            String receiverAddress,
            String receiverMobile,
            Long quantity
    ) {
        var product = productRepository.findByIdOrThrow(productId);

        Preconditions.vaildate(product.canProvide(quantity), ErrorCode.NOT_ENOUGH_STOCK);

        var user = userRepository.findByIdOrThrow(userId, ErrorCode.NOT_FOUND_USER);

        var receivers = new Receiver(receiverName, receiverAddress, receiverMobile);

        var order = orderRepository.save(Order.create(receivers, user));

        var orderProduct = orderProductRepository.save(new OrderProduct(order, product, quantity));

        // 주문 생성 완료
        product.decreaseStock(quantity);

        product.mapToOrderProduct(orderProduct);
        order.mapToOrderProduct(orderProduct);
    }
}
