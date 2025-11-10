package com.kt.controller.product;

import com.kt.common.ApiResult;
import com.kt.dto.product.ProductRequest;
import com.kt.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Product")
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService; //DI 생성자로 의존성 주입

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<Void> create(@RequestBody @Valid ProductRequest.Create request) {
        productService.create(
                request.getName(),
                request.getPrice(),
                request.getQuantity()
        );
        return ApiResult.ok();
    }

    @PutMapping ("/{id}")
    public ApiResult<Void> update(@RequestBody @Valid ProductRequest.Update request, @PathVariable Long id) {
        productService.update(id, request.getName(), request.getPrice(), request.getQuantity());

        return ApiResult.ok();
    }

    @PatchMapping("/{id}/sold-out")
    public void soldOut (@PathVariable Long id) {
        productService.soldOut(id);
    }

    @PatchMapping("/{id}/activate")
    public ApiResult<Void> activate(@PathVariable Long id) {
        productService.activate(id);
        return ApiResult.ok();
    }

    @PatchMapping("/{id}/in-activate")
    public ApiResult<Void> inActivate(@PathVariable Long id) {
        productService.inActivate(id);
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    public ApiResult<Void> remove (@PathVariable Long id) {
        productService.delete(id);
        return ApiResult.ok();
    }
}
