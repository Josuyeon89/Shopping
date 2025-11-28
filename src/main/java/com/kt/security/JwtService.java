package com.kt.security;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;

    public String issue(Long id, Date expiration) {

        // id 값은 jwt의 식별자같은 개볌 -> User의 id 값

        return Jwts.builder()
                .subject("shopping")
                .issuer("suyeon")
                .issuedAt(new Date())
                .id(id.toString())
                .expiration(expiration)
                .signWith(jwtProperties.getSecret())
                .compact();
    }

    public Date getAccessTokenExpiration() {
        return jwtProperties.getAccessTokenExpiration();
    }

    public Date getRefreshTokenExpiration() {
        return jwtProperties.getRefreshTokenExpiration();
    }

    public boolean validate (String token) {
        return Jwts.parser()
                .verifyWith(jwtProperties.getSecret())
                .build()
                .isSigned(token);
    }

    public Long parseId (String token) {
        var id = Jwts.parser()
                .verifyWith(jwtProperties.getSecret())
                .build()
                .parseSignedClaims(token)
                .getPayload().getId();

        return Long.valueOf(id);
    }
}
