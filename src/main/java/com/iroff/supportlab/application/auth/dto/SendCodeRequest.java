package com.iroff.supportlab.application.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SendCodeRequest(
	@NotBlank(message = "휴대전화 번호는 필수입니다.")
	@Pattern(
		regexp = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$",
		message = "유효한 한국 휴대전화 번호여야 합니다 (예: 01012345678)."
	)
	String phone
) {
}
