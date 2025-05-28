package com.iroff.supportlab.application.user.dto;

import com.iroff.supportlab.adapter.auth.in.web.validation.KoreanPhone;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignUpUserRequest(
	@Email(message = "이메일 형식이 아닙니다.")
	String email,

	@NotBlank(message = "비밀번호는 필수입니다.")
	String password,

	@NotBlank(message = "이름은 필수입니다.")
	String name,

	@NotBlank(message = "휴대폰 번호는 필수입니다.")
	@KoreanPhone
	String phone,

	@NotNull(message = "이용 약관 동의 여부는 필수입니다.")
	Boolean termsOfServiceAgreed,

	@NotNull(message = "개인정보 수집 및 이용 동의 여부는 필수입니다.")
	Boolean privacyPolicyAgreed,

	@NotNull(message = "마케팅 수신 동의 여부는 필수입니다.")
	Boolean marketingAgreed
) {
}
