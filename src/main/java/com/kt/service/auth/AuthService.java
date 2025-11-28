package com.kt.service.auth;

import com.kt.common.CustomException;
import com.kt.common.ErrorCode;
import com.kt.common.Preconditions;
import com.kt.repository.user.UserRepository;
import com.kt.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public Pair<String, String> login(String loginId, String password) {
        var user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(ErrorCode.FAIL_LOGIN));

        Preconditions.validate(passwordEncoder.matches(password, user.getPassword()), ErrorCode.FAIL_LOGIN);

        // 로그인 성공 처리 -> JWT 토큰 발급
        // 헤더에 넣어서 줄 수도 있고 바디에 넣어서 줄 수도 있고(v) 쿠키에 넣어줄 수도 있고

        var accessToken = jwtService.issue(user.getId(), jwtService.getAccessTokenExpiration());
        var refreshToken = jwtService.issue(user.getId(), jwtService.getRefreshTokenExpiration());

        return Pair.of(accessToken, refreshToken);
    }

}
