package com.iroff.supportlab.application.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "이메일 찾기 응답 DTO")
public record FindEmailResponse(
    @Schema(description = "찾은 이메일 주소", example = "user@example.com")
    String email
) {}
