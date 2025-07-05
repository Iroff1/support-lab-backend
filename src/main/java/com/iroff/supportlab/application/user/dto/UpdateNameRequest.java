package com.iroff.supportlab.application.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "사용자 이름 변경 요청 DTO")
public record UpdateNameRequest(
	@Schema(description = "새로운 이름")
	@NotBlank(message = "이름은 필수입니다.")
	String newName
) {
}