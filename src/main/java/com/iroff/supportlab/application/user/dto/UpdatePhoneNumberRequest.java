package com.iroff.supportlab.application.user.dto;

import com.iroff.supportlab.adapter.auth.in.web.validation.KoreanPhone;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원 전화번호 수정 요청 DTO")
public record UpdatePhoneNumberRequest(
	@Schema(description = "수정할 전화번호", example = "01012345678")
	@KoreanPhone
	String newPhone
) {
}