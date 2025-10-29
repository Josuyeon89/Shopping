package com.kt.controller;

import com.kt.dto.UserCreateRequest;
import com.kt.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping ("/users")
    @ResponseStatus(HttpStatus.CREATED)
    // userService를 DI 받아야 함
    // DI받는 방식: 생성자 주입 -> 불변을 위해서

    // loginId, password, name, birthday
    // json형태의 body에 담겨서 post 요청으로 /users로 들어오면
    // @RequestBody를 보고 jacksonObjectMapper가 동작해서 json을 읽어서 dto로 변환
    public void create(@RequestBody UserCreateRequest userCreateRequest) {
        userService.create(userCreateRequest);
    }

}
