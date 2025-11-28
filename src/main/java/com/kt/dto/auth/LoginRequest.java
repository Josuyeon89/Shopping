package com.kt.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginRequest(
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9]{8,20}$", message = "아이디는 영문 대소문자, 숫자입력이 가능합니다.")
        String loginId,
        @NotBlank
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^])[A-Za-z\\d!@#$%^]{8,}$")
        String password
) {
}
