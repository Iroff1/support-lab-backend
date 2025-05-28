package com.iroff.supportlab.domain.auth.model.vo;

import lombok.Getter;

@Getter
public enum VerificationType {
	SIGN_UP_CODE("signupCode"),
	FIND_PASSWORD_CODE("findPasswordCode"),
	SIGN_UP_VERIFIED("signupVerified"),
	FIND_PASSWORD_VERIFIED("findPasswordVerified");

	private final String value;

	VerificationType(String value) {
		this.value = value;
	}

}
