package com.iroff.supportlab.application.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "비밀번호 변경 DTO")
public record ChangePasswordRequest(
	@Schema(description = "비밀번호 변경 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
	@NotBlank(message = "토큰 값은 필수입니다.")
	String token,

	@Schema(description = "새로운 비밀번호")
	@NotBlank(message = "비밀번호는 필수입니다.")
	String password
) {
}
