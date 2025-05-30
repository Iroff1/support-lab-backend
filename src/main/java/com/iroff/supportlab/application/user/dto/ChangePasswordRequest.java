package com.iroff.supportlab.application.user.dto;

import com.iroff.supportlab.adapter.auth.in.web.validation.KoreanPhone;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(
	@Email(message = "이메일 형식이 아닙니다.")
	String email,

	@NotBlank(message = "이름은 필수입니다.")
	String name,

	@NotBlank(message = "휴대폰 번호는 필수입니다.")
	@KoreanPhone
	String phone
) {
}
