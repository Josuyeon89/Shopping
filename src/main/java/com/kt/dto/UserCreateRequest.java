package com.kt.dto;

import com.kt.domain.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserCreateRequest(
        String loginId,
        String password,
        String name,
        String email,
        String mobile,
        Gender gender,
        LocalDate birthday, // YYYY-mm-dd
        LocalDateTime createTime,
        LocalDateTime updateTime
){}
