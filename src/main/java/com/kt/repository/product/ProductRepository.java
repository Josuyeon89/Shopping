package com.kt.repository.product;

import com.kt.common.CustomException;
import com.kt.common.ErrorCode;
import com.kt.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    default Product findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));
    }
}
