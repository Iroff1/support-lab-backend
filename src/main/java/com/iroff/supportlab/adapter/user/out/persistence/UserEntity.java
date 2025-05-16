package com.iroff.supportlab.adapter.user.out.persistence;

import com.iroff.supportlab.adapter.common.out.persistence.BaseTimeEntity;
import com.iroff.supportlab.domain.user.model.Role;
import com.iroff.supportlab.domain.user.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
	name = "users",
	uniqueConstraints = {
		@UniqueConstraint(name = "uk_user_email", columnNames = "email"),
		@UniqueConstraint(name = "uk_user_phone", columnNames = "phone")
	}
)
public class UserEntity extends BaseTimeEntity implements User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private String phone;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	@Column(nullable = false)
	private Boolean privacyPolicyAgreed;

	@Column(nullable = false)
	private Boolean marketingAgreed;
}
