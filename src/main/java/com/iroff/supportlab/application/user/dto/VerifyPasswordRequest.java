package com.iroff.supportlab.application.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "(정보 수정용) 비밀번호 인증 DTO")
public record VerifyPasswordRequest(
	@Schema(description = "비밀번호")
	@NotBlank(message = "비밀번호는 필수입니다.")
	String password
) {
}
