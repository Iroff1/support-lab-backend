package com.iroff.supportlab.application.user.dto;

import com.iroff.supportlab.adapter.auth.in.web.validation.KoreanPhone;

import jakarta.validation.constraints.NotBlank;

public record FindEmailRequest(
	@NotBlank(message = "이름은 필수입니다.")
	String name,

	@KoreanPhone
	String phone) {
}
