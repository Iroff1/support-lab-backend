package com.iroff.supportlab.application.user.dto;

import com.iroff.supportlab.adapter.auth.in.web.validation.KoreanPhone;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "회원가입 요청 DTO")
public record SignUpUserRequest(
	@Schema(description = "이메일", example = "user@example.com")
	@Email(message = "이메일 형식이 아닙니다.")
	String email,

	@Schema(description = "비밀번호")
	@NotBlank(message = "비밀번호는 필수입니다.")
	String password,

	@Schema(description = "사용자 이름", example = "홍길동")
	@NotBlank(message = "이름은 필수입니다.")
	String name,

	@Schema(description = "휴대폰 번호", example = "01012345678")
	@NotBlank(message = "휴대폰 번호는 필수입니다.")
	@KoreanPhone
	String phone,

	@Schema(description = "이용약관 동의 여부")
	@NotNull(message = "이용 약관 동의 여부는 필수입니다.")
	Boolean termsOfServiceAgreed,

	@Schema(description = "개인정보 수집 및 이용 동의 여부")
	@NotNull(message = "개인정보 수집 및 이용 동의 여부는 필수입니다.")
	Boolean privacyPolicyAgreed,

	@Schema(description = "마케팅 수신 동의 여부")
	@NotNull(message = "마케팅 수신 동의 여부는 필수입니다.")
	Boolean marketingAgreed
) {
}
