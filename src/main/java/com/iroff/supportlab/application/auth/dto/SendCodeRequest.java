package com.iroff.supportlab.application.auth.dto;

import com.iroff.supportlab.adapter.auth.in.web.validation.KoreanPhone;
import com.iroff.supportlab.domain.auth.model.vo.VerificationType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "인증 코드 발송 요청 DTO")
public record SendCodeRequest(
	@Schema(description = "인증 타입 (SIGN_UP_CODE: 회원가입, FIND_EMAIL_CODE: 이메일 찾기, FIND_PASSWORD_CODE: 비밀번호 찾기)", example = "SIGN_UP_CODE", allowableValues = {
		"SIGN_UP_CODE", "FIND_EMAIL_CODE", "FIND_PASSWORD_CODE"})
	@NotNull(message = "검증 타입은 필수입니다.")
	VerificationType type,

	@Schema(description = "휴대폰 번호", example = "01012345678")
	@NotBlank(message = "휴대전화 번호는 필수입니다.")
	@KoreanPhone
	String phone
) {
}
