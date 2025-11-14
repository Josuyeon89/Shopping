package com.kt.repository.product;

import com.kt.domain.product.Product;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {

    @AutoClose
    private ProductRepository productRepository;

    @Test
    void 이름으로_상품_검색  () {
        // 준비단계 given
        // 먼저 상품을 저장해놔야 검색했을 때 있는지 없는지 알 수 있음
        var product = productRepository.save(new Product(
                "테스트 상품명",
                100_000L,
                10L
        ));

        // 실행 when
        var foundedProduct = productRepository.findByName("테스트 상품명");

        assertThat(foundedProduct).isPresent();

        productRepository.deleteAll();

    }

}