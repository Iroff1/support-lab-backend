package com.iroff.supportlab.application.user.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(
	@NotBlank(message = "토큰 값은 필수입니다.")
	String token,

	@NotBlank(message = "비밀번호는 필수입니다.")
	String password
) {
}
