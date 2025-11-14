package com.kt.domain.product;

import java.util.ArrayList;
import java.util.List;

import com.kt.common.BaseEntity;
import com.kt.common.ErrorCode;
import com.kt.common.Preconditions;
import com.kt.domain.orderproduct.OrderProduct;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

@Entity
@Getter
@NoArgsConstructor
public class Product extends BaseEntity {   //POJO임 -> Component가 없다. (Entity 들어가보면 없음)

    /*
    테스트 코드 -> 무조건 해야함. 특히 API테스트 (Controller, Swagger, Postman)는 한번은 해봐야 함
    테스트를 할 수 있는 방법
    1. 테스트 코드 작성
    2. Postman으로 실제 서버에 쏴보기
    3. swagger로 테스트 해보기
    4. curl로 테스트 해보기 (리눅스, 터미널 등)
    5. Intellij Ultimate 에서는 HTTP 클라이언트를 지원한다


     테스트의 범위
    1. 단위 테스트 : 가장 작은 단위의 기능을 테스트하는 것 (메서드, 클래스, 아키텍처의 어떤 레이어 등등) - 의존성이 없을 때
    2. 통합 테스트 : 여러 단위를 다 모아서 테스트 하는 것 - 속도 느림

    통합테스트에서 더 빠르게 하기  위해 하는 방법 -> Mocking (가짜객체)
    서비스 레이어를 테스트 할 때 repository를 Mocking해서 테스트를 빠르게 함 -> repository를 호출하면 ~~한 결과가 나올거야
    하지만 시스템한테 거짓말을 하는 것 -> 개발자가 거짓말을 해야하는데 실수가 나올 때 나중에 그 실수를 찾기 힘듦
    잘 써야함

    테스트 코드 5개 원칙 - FIRST
    F : Fast - 빠르게 실행되어야 한다.
    I : Independent, Isolated : 긱긱의 테스트가 독립적으로 실행되어야 한다.
    R : Repeatable : 하나의 테스트를 반복해서 실행할 수 있어야 하고 동일한 결과를 내야한다.
    S : Self-validating : 테스트가 스스로 검증할 수 있어야 한다.
    T : Timely : 적절한 시점에 작성되어야 한다. (바로바로 테스트 코드 작성)

    테스트 코드 만들기 단축키 - cmd + shift + T
    */

	private String name;
	private Long price;
	private Long stock;
	@Enumerated(EnumType.STRING)
	private ProductStatus status = ProductStatus.ACTIVED;

	@OneToMany(mappedBy = "product")
	private final List<OrderProduct> orderProducts = new ArrayList<>();

    public Product(String name, Long price, Long stock) {
        Preconditions.vaildate(Strings.isNotBlank(name), ErrorCode.INVALID_PARAMETER);
        Preconditions.vaildate(price>=0, ErrorCode.INVALID_PARAMETER);
        Preconditions.vaildate(stock>=0, ErrorCode.INVALID_PARAMETER);

        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public void update (String name, Long price, Long stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public void soldOut() {
        this.status = ProductStatus.SOLD_OUT;
    }

    public void inActivated() {
        this.status = ProductStatus.IN_ACTIVED;
    }

    public void activated() {
        this.status = ProductStatus.ACTIVED;
    }

    public void deleted() {
        this.status = ProductStatus.DELETED;
    }

    public void decreaseStock(Long quantity) {
        this.stock-= quantity;
    }
    public void increaseStock(Long quantity) {
        this.stock -= quantity;
    }

    public boolean canProvide(Long quantity) {
        return this.stock >= quantity;
    }

    public void mapToOrderProduct(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
    }

	//생성
	//수정
	//삭제
	//조회(리스트, 단건)
	//상태변경
	//재고수량감소
	//재고수량증가

}
