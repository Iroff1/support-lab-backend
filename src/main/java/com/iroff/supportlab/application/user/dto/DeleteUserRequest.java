package com.iroff.supportlab.application.user.dto;

import jakarta.validation.constraints.NotBlank;

public record DeleteUserRequest(
	@NotBlank(message = "비밀번호 입력은 필수입니다.")
	String password
) {
}
