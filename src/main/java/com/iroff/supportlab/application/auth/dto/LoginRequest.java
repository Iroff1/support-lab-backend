package com.iroff.supportlab.application.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 요청 DTO")
public record LoginRequest(
	@Schema(description = "이메일", example = "user@example.com")
	String email,
	
	@Schema(description = "비밀번호")
	String password
) {
}
