package com.kt.service;

import com.kt.domain.user.Gender;
import com.kt.domain.user.Role;
import com.kt.domain.user.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    @Test
    void 주문_생성 () {

    }

    void 동시에_100명_주문 () {
        for(int i=0 ; i<100 ; i++){
            new User(
                    "testuser"+i,
                    "password",
                    "Test User"+i,
                    "email"+i,
                    "010-2222-1111"+i,
                    Gender.FEMALE,
                    LocalDate.now(),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    Role.USER

            );
        }
    }

}