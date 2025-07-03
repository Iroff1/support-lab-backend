package com.iroff.supportlab.application.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdatePasswordRequest(
	@NotBlank(message = "기존 비밀번호를 입력해야 합니다.")
	String oldPassword,

	@NotBlank(message = "새로운 비밀번호를 입력해야 합니다.")
	String newPassword
) {
}
