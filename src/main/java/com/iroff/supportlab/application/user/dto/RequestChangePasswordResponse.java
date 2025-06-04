package com.iroff.supportlab.application.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "비밀번호 변경 요청 응답 DTO")
public record RequestChangePasswordResponse(
	@Schema(description = "비밀번호 변경 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
	String token
) {
}
