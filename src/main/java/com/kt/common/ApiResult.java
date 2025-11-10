package com.kt.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

// API 응답을 표준화하기 위한 클래스
// Spring에서는 Http응답을 처리해주는 객체 존재 -> ResponseEntity
@Getter
@AllArgsConstructor
public class ApiResult<T> {
    private String code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    // 정적 팩토리 메서드 패턴 => 객체한테 이름을 달아주는 역할
    // new ->  이름을 달아줘서 구분하기 쉽게
    // 관례가 존재하는데 of(자체적으로 생성할 때), from(외부에 의해서 생성될 떄)
    public static ApiResult<Void> ok() {
        return ApiResult.of("ok", "성공", null);
    }

    public static <T> ApiResult<T> ok(T data) {
        return ApiResult.of("ok", "성공", data);
    }

    private static <T> ApiResult<T> of(String code, String message, T data) {
        return new ApiResult<>(code, message, data);
    }


}