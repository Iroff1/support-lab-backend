package com.iroff.supportlab.application.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CheckEmailExistsRequest(
	@Email
	@NotBlank(message = "이메일 입력은 필수입니다.")
	String email
) {
}
