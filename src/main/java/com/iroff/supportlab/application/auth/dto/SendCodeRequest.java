package com.iroff.supportlab.application.auth.dto;

import com.iroff.supportlab.adapter.auth.in.web.validation.KoreanPhone;
import com.iroff.supportlab.domain.auth.model.vo.VerificationType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SendCodeRequest(
	@NotNull(message = "검증 타입은 필수입니다.")
	VerificationType type,

	@NotBlank(message = "휴대전화 번호는 필수입니다.")
	@KoreanPhone
	String phone
) {
}
