package com.kt.domain.product;

import java.util.ArrayList;
import java.util.List;

import com.kt.common.BaseEntity;
import com.kt.domain.orderproduct.OrderProduct;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {
	private String name;
	private Long price;
	private Long stock;
	@Enumerated(EnumType.STRING)
	private ProductStatus status = ProductStatus.ACTIVED;

	@OneToMany(mappedBy = "product")
	private List<OrderProduct> orderProducts = new ArrayList<>();

    public Product(String name, Long price, Long stock) {
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

	//생성
	//수정
	//삭제
	//조회(리스트, 단건)
	//상태변경
	//재고수량감소
	//재고수량증가

}
