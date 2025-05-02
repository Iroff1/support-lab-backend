package com.iroff.supportlab.presentation.user.out.persistence;

import java.time.LocalDateTime;

import com.iroff.supportlab.domain.user.model.Role;
import com.iroff.supportlab.domain.user.model.User;
import com.iroff.supportlab.presentation.global.out.persistence.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "user")
public class UserEntity extends BaseTimeEntity implements User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String phone;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getPhone() {
		return phone;
	}

	@Override
	public Role getRole() {
		return role;
	}

	@Override
	public LocalDateTime getCreatedAt() {
		return super.getCreatedAt();
	}

	@Override
	public LocalDateTime getModifiedAt() {
		return super.getModifiedAt();
	}
}
