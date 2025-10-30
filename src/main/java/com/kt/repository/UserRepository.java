package com.kt.repository;


import com.kt.domain.User;
import com.kt.dto.UserCreateRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public void save(User user) {
        // 서비스에서 dto를 도메인(비지니스모델)으로 바꾼다음 전달
        var sql = "INSERT INTO MEMBER (id, loginId, password, name, birthday, email, mobile, gender, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, user.getId(),
                user.getLoginId(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getEmail(),
                user.getMobile(),
                user.getGender()
        );
    }

     public Long selectMaxId() {
        var sql = "SELECT MAX(id) FROM MEMBER";

        var maxId = jdbcTemplate.queryForObject(sql, Long.class);
        return maxId == null ? 0L : maxId;
    }
}