package com.kt.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductStatus {
	ACTIVED("판매중"),
	SOLD_OUT("품절"),
	IN_ACTIVED("판매중지"),
    DELETED("삭제");

	private final String description;
}
