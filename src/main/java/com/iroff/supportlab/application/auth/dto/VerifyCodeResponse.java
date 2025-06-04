package com.iroff.supportlab.application.auth.dto;

import com.iroff.supportlab.application.auth.dto.vo.VerifyCodeResponseStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "인증 코드 검증 응답 DTO")
public record VerifyCodeResponse(
    @Schema(description = "검증 상태 (SUCCESS: 성공, FAIL: 실패)", example = "SUCCESS")
    VerifyCodeResponseStatus status
) {}
