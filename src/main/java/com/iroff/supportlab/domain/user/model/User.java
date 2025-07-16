package com.iroff.supportlab.domain.user.model;

import com.iroff.supportlab.domain.common.model.BaseTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
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