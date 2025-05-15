package com.iroff.supportlab.application.auth.dto;

import com.iroff.supportlab.adapter.auth.in.web.validation.KoreanPhone;

import jakarta.validation.constraints.NotBlank;

public record SendCodeRequest(
	@NotBlank(message = "휴대전화 번호는 필수입니다.")
	@KoreanPhone
	String phone
) {
}
