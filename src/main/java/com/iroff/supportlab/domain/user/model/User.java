package com.iroff.supportlab.domain.user.model;

import java.time.LocalDateTime;

import com.iroff.supportlab.domain.common.model.BaseTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class User extends BaseTime {
	private Long id;
	private String password;
	private String email;
	private String name;
	private String phone;
	private Role role;
	private Boolean termsOfServiceAgreed;
	private Boolean privacyPolicyAgreed;
	private Boolean marketingAgreed;
	private Boolean active;
	private Long version;

	public User(Long id, String password, String email, String name, String phone, Role role,
		Boolean termsOfServiceAgreed, Boolean privacyPolicyAgreed, Boolean marketingAgreed, Boolean active,
		LocalDateTime createdAt, LocalDateTime modifiedAt, Long version) {
		super(createdAt, modifiedAt);
		this.id = id;
		this.password = password;
		this.email = email;
		this.name = name;
		this.phone = phone;
		this.role = role;
		this.termsOfServiceAgreed = termsOfServiceAgreed;
		this.privacyPolicyAgreed = privacyPolicyAgreed;
		this.marketingAgreed = marketingAgreed;
		this.active = active;
		this.version = version;
	}

	public void changeName(String name) {
		this.name = name;
	}

	public void changePassword(String password) {
		this.password = password;
	}

	public void changePhone(String phone) {
		this.phone = phone;
	}

	public void changeMarketingAgreed(Boolean agreed) {
		this.marketingAgreed = agreed;
	}

	public void changeActive(Boolean active) {
		this.active = active;
	}
}