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

	@Schema(description = "전화번호 변경 인증 코드")
	UPDATE_PHONE_CODE("updatePhoneCode"),

	VERIFY_EMAIL_CODE("verifyEmailCode"),
	SIGN_UP_VERIFIED("signupVerified"),
	VERIFY_EMAIL_VERIFIED("verifyEmailVerified"),
	FIND_EMAIL_VERIFIED("findEmailVerified"),
	FIND_PASSWORD_VERIFIED("findPasswordVerified"),
	UPDATE_PHONE_VERIFIED("updatePhoneVerified"),
	RESET_PASSWORD("resetPassword");

	private final String value;

	VerificationType(String value) {
		this.value = value;
	}
}
