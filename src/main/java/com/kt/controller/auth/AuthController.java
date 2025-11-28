package com.kt.controller.auth;

import com.kt.common.ApiResult;
import com.kt.dto.auth.LoginRequest;
import com.kt.dto.auth.LoginResponse;
import com.kt.service.auth.AuthService;
import io.swagger.v3.oas.models.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    /*
    * 인증 관련 컨트롤러 구현
    * 인증방식 1 : 세션 기반 인증 -> 서버 쪽에서 작은 공간에 사용자 정보를 저장 - 만료시간
    *            서버에서 관리하기 때문에 보안성 굿
    *            A 서버에서 인가를 해줌 -> 세션에 저장을 하고 있음 / 그럼 B서버 세션에는 인가된 정보가 있나? 없음.
    *            해결책 : 세션클러스터링, 스티키 세션 -> redis등 외부 저장소를 통해서 단일 세션
    * 인증방식 2 : 토큰 기반 인증(JWT) -> 사용자가 토큰을 가지고 있다가 요청할 때마다 같이 줌
    *            서버 입장에서는 해당 토큰에 대한 신뢰 X -> 매번 검사를 해야함
    *            but, 서버에서 관리하지 않아 부하 적음, 분산 환경에 유리
    * */

    @PostMapping("/login")
    public ApiResult<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        var pair = authService.login(request.loginId(), request.password());

        return ApiResult.ok(new LoginResponse(pair.getFirst(), pair.getSecond()));
    }
}
