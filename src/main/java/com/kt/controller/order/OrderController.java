package com.kt.controller.order;

import com.kt.common.ApiResult;
import com.kt.dto.order.OrderRequest;
import com.kt.security.CurrentUser;
import com.kt.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // controller+responsebody
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    public ApiResult<Void> create (@AuthenticationPrincipal CurrentUser currentUser,
                                   @RequestBody @Valid OrderRequest.Creat orderRequest) {
        orderService.create(
                currentUser.getId(),
                orderRequest.productId(),
                orderRequest.receiverName(),
                orderRequest.receiverAddress(),
                orderRequest.receiverMobile(),
                orderRequest.quantity()
        );
        return ApiResult.ok();
    }
}
