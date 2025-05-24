package com.iroff.supportlab.application.auth.dto;

import com.iroff.supportlab.adapter.auth.in.web.validation.KoreanPhone;
import com.iroff.supportlab.domain.auth.model.vo.VerificationType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VerifyCodeRequest(
	@NotNull(message = "검증 타입은 필수입니다.")
	VerificationType type,

	@KoreanPhone
	String phone,

	@NotBlank(message = "인증 코드 입력은 필수입니다.")
	String code) {
}
