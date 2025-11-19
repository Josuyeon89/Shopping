package com.kt.service.order;

import com.kt.common.ErrorCode;
import com.kt.common.Lock;
import com.kt.common.Preconditions;
import com.kt.domain.order.Order;
import com.kt.domain.order.Receiver;
import com.kt.domain.orderproduct.OrderProduct;
import com.kt.domain.product.Product;
import com.kt.domain.user.User;
import com.kt.repository.order.OrderRepository;
import com.kt.repository.orderproduct.OrderProductRepository;
import com.kt.repository.product.ProductRepository;
import com.kt.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final RedisProperties redisProperties;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;

    @Lock(key = Lock.Key.STOCK, index = 1)  // index가 1이라는 뜻은 productId를 락 키로 사용한다는 뜻
    public void create(
            Long userId,
            Long productId,
            String receiverName,
            String receiverAddress,
            String receiverMobile,
            Long quantity
    ) {
        Product product = productRepository.findByIdOrThrow(productId);

        Preconditions.validate(product.canProvide(quantity), ErrorCode.NOT_ENOUGH_STOCK);

        User user = userRepository.findByIdOrThrow(userId, ErrorCode.NOT_FOUND_USER);

        Receiver receiver = new Receiver(receiverName, receiverAddress, receiverMobile);

        Order order = orderRepository.save(Order.create(receiver, user));

        OrderProduct orderProduct = orderProductRepository.save(new OrderProduct(order, product, quantity));

        // 주문 생성 완료
        product.decreaseStock(quantity);

        product.mapToOrderProduct(orderProduct);
        order.mapToOrderProduct(orderProduct);
    }
}