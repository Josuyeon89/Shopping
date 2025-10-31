package com.kt.dto;

import com.kt.domain.Gender;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


public record UserCreateRequest(
        @NotBlank   // null이나 공백은 안됨
        String loginId,
        @NotBlank
        // 최소 8자 이상, 대문자 소문자 특수문자 포함 -> 정규식 사용
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^])[A-Za-z\\d!@#$%^]{8,}$")
        String password,
        @NotBlank
        String name,
        @NotBlank
        // 이메일도 정규식 사용
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        String email,
        @NotBlank
        @Pattern(regexp = "^(01[016789])-?\\d{3,4}-?\\d{4}$")
        String mobile,
        @NotNull    //enum값이기 때문에 공백이 있을 수 있음
        Gender gender,
        @NotNull    // 2002-08-09
        LocalDate birthday
){}
