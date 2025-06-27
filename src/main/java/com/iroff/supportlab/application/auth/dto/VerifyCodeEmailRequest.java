package com.iroff.supportlab.application.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "이메일 검증 코드 검증 DTO")
public record VerifyCodeEmailRequest(
	@Schema(description = "이메일", example = "user@example.com")
	@Email(message = "이메일 형식이 맞지 않습니다.")
	@NotBlank(message = "이메일 입력은 필수입니다.")
	String email,

	@Schema(description = "인증 코드", example = "123456")
	@NotBlank(message = "인증 코드 입력은 필수입니다.")
	String code
) {
}
