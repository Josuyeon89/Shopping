package com.kt.repository.product;

import com.kt.domain.product.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    private Product product;

    @BeforeEach
    void setUp() {
        product = productRepository.save(
                new Product(
                        "테스트 상품명",
                        100_000L,
                        10L
                ));

    }

    @Test
    void 이름으로_상품_검색  () {
        // 준비단계 given
        // 먼저 상품을 저장해놔야 검색했을 때 있는지 없는지 알 수 있음
        // 실행 when
        var foundedProduct = productRepository.findByName("테스트 상품명");

        assertThat(foundedProduct).isPresent();
        // 실제로 존재할때는 true, 없으면 false
    }
}