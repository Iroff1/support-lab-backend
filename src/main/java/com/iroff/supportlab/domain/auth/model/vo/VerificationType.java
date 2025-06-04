package com.iroff.supportlab.domain.auth.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "인증 타입")
@Getter
public enum VerificationType {
	@Schema(description = "회원가입 인증 코드")
	SIGN_UP_CODE("signupCode"),

	@Schema(description = "이메일 찾기 인증 코드")
	FIND_EMAIL_CODE("findEmailCode"),

	@Schema(description = "비밀번호 찾기 인증 코드")
	FIND_PASSWORD_CODE("findPasswordCode"),

	SIGN_UP_VERIFIED("signupVerified"),
	FIND_EMAIL_VERIFIED("findEmailVerified"),
	FIND_PASSWORD_VERIFIED("findPasswordVerified"),
	RESET_PASSWORD("resetPassword");

	private final String value;

	VerificationType(String value) {
		this.value = value;
	}
}
