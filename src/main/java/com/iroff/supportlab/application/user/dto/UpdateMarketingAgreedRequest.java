package com.iroff.supportlab.application.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "회원 마케팅 수신 동의 수정 요청 DTO")
public record UpdateMarketingAgreedRequest(
	@NotNull(message = "마케팅 수신 동의 여부 설정은 필수입니다.")
	Boolean marketingAgreed
) {
}