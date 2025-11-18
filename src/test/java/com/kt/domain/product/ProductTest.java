package com.kt.domain.product;

import com.kt.common.CustomException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static com.kt.domain.product.QProduct.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

// 테스트코드 메서드 제목 작성 방법
// 1 : DisplayName 어노테이션
// 2 : 메서드명 자체를 한글로 작성 -> 공백은 _(언더바)로 대체 (v)

class ProductTest {
    // POJO인 Product가 객체 생성이 잘 됨?
    @Test
    void 객체_생성_성공() {
        var product = new Product(
                "테스트 상품명",
                100_000L,
                10L
        );
        // product의 이름 필드의 값이 테스트 상품명 이 맞냐? -> true or false -> FIRST원칙 중 Self-validating -> assertThat, assertEquals
        assertThat(product.getName()).isEqualTo("테스트 상품명");
        assertThat(product.getPrice()).isEqualTo(100_000L);
        assertThat(product.getStock()).isEqualTo(10L);
    }

//    //하고자하는 거 적고 뒤에 이유
//    @Test
//    void 상품_생성_실패__상품명_공백 () {   //product가 null이냐? -> 실패함
//        // 상품명이 공백이면 throw 던저줘야할거같다 -> Product 도메인의 생성자에서
//        // 테스트코드에서도 throw exception
//            assertThrowsExactly(IllegalArgumentException.class, () ->      // 람다식에서 단일 실행문이라면 중괄호와 반환값 생략 가능
//            new Product(
//                    " ",
//                    100_000L,
//                    10L
//            ), "상품명은 필수입니다 (test)");
//    }
//
//    @Test
//    void 상품_생성_실패__상품명_null () {
////        var product = new Product(
////                null,
////                100_000L,
////                10L
////        );
////
////        assertThat(product).isNull();   //product가 null이냐? -> 실패함
//        // 상품명이 공백이면 throw 던저줘야할거같다 -> Product 도메인의 생성자에서
//        // 테스트코드에서도 throw exception
//        assertThrowsExactly(IllegalArgumentException.class, () ->      // 람다식에서 단일 실행문이라면 중괄호와 반환값 생략 가능
//                new Product(
//                        null,
//                        100_000L,
//                        10L
//                ), "상품명은 필수입니다 (test)");
//    }

    @ParameterizedTest
    @NullAndEmptySource
    void 상품_생성_실패__상품명_null_이거나_공백 (String name) {
        assertThrowsExactly(CustomException.class, () ->
            new Product(
                    name,
                    100_000L,
                    10L
            )
        );
    }

    @Test
    void 상품_생성_살패_가격이_음수 () {
        assertThrowsExactly(CustomException.class, () -> new Product(
                "테스트 상품명",
                -1L,
                10L
        ));
    }

    @Test
    void 상품_생성_실패__가격이_null() {
        assertThrowsExactly(IllegalArgumentException.class, () ->
                new Product(
                        "테스트 상품명",
                        null,
                        10L
                ));
    }
}