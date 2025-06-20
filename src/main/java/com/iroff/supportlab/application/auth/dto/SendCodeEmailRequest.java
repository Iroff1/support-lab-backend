package com.iroff.supportlab.application.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "이메일 검증 코드 발송 DTO")
public record SendCodeEmailRequest(
	@Schema(description = "이메일", example = "user@example.com")
	@Email(message = "이메일 형식이 맞지 않습니다.")
	@NotBlank(message = "이메일 입력은 필수입니다.")
	String email
) {
}
