package com.kt.service.user;

import java.time.LocalDateTime;

import com.kt.common.ErrorCode;
import com.kt.common.Preconditions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.domain.user.User;
import com.kt.dto.user.UserCreateRequest;
import com.kt.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

// 구현체가 하나 이상 필요로 해야 인터페이스가 의미가 있다
// 인터페이스 : 구현체 1:1로 다 나눠야함?
// 관례를 지키려고 추상화를 굳이 하는 것을 관습적 추상화 -> 굳이~
// -> 필요한 곳에서만 추상화 하자

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
	// private final UserJDBCRepository userJDBCRepository; 추억속으로
	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

	// 트랜잭션 처리해줘
	// PSA - Portable Service Abstraction
	// 환경설정을 살짝 바꿔서 일관된 서비스를 제공하는 것
	public void create(UserCreateRequest request) {
			var newUser = User.normalUser(
				request.loginId(),
                passwordEncoder.encode(request.password()),
				request.name(),
				request.email(),
				request.mobile(),
				request.gender(),
				request.birthday(),
				LocalDateTime.now(),
				LocalDateTime.now()
            );

			userRepository.save(newUser);
	}

	public boolean isDuplicateLoginId(String loginId) {
		return userRepository.existsByLoginId(loginId);
	}

	public void changePassword(Long id, String oldPassword, String password) {
		var user = userRepository.findByIdOrThrow(id, ErrorCode.NOT_FOUND_USER);

        Preconditions.validate(user.getPassword().equals(oldPassword), ErrorCode.DOES_NOT_MATCH_OLD_PASSWORD);  // 설정한 비번이 이전 비번과 같지 않을 때 -> DOES_NOT_MATCH_OLD_PASSWORD 가 떠야한다.
        // validate();에서 앞의 조건이 맞지 않을 때 뒤의 조건 실행됨

        Preconditions.validate(!oldPassword.equals(password), ErrorCode.CAN_NOT_ALLOWED_SAME_PASSWORD);
		user.changePassword(password);
	}

	// Pageable 인터페이스
	public Page<User> search(Pageable pageable, String keyword) {
		return userRepository.findAllByNameContaining(keyword, pageable);
	}

	public User detail(Long id) {
		return userRepository.findByIdOrThrow(id, ErrorCode.NOT_FOUND_USER);
	}

	public void update(Long id, String name, String email, String mobile) {
		var user = userRepository.findByIdOrThrow(id, ErrorCode.NOT_FOUND_USER);

		user.update(name, email, mobile);
	}

	public void delete(Long id) {
		userRepository.deleteById(id);
		// 삭제에는 두가지 개념 - softdelete, harddelete
		// var user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
		// userRepository.delete(user);
	}
}
