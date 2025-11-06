package com.kt.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 1. domain과 entity를 분리해야
// 2. 굳이? 같이쓰지뭐
@Getter
@NoArgsConstructor
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String loginId;
	private String password;
	private String name;
	private String email;
	private String mobile;
	// ordinal : enum의 순서를 DB에 저장 => 절대 사용하지마세욤
	// string : enum의 값 DB에 저장
	@Enumerated(EnumType.STRING)
	private Gender gender;
	private LocalDate birthday;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public User(String loginId, String password, String name, String email, String mobile, Gender gender,
		LocalDate birthday, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.loginId = loginId;
		this.password = password;
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.gender = gender;
		this.birthday = birthday;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public void changePassword(String password) {
		this.password = password;
	}

	public void update(String name, String email, String mobile) {
		this.name = name;
		this.email = email;
		this.mobile = mobile;
	}
}
