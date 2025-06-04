package com.iroff.supportlab.application.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "회원가입 응답 DTO")
public record SignUpUserResponse(
	@Schema(description = "사용자 ID", example = "1")
	Long id,
	
	@Schema(description = "이메일", example = "user@example.com")
	String email,
	
	@Schema(description = "사용자 이름", example = "홍길동")
	String name,
	
	@Schema(description = "휴대폰 번호", example = "01012345678")
	String phone,
	
	@Schema(description = "이용약관 동의 여부")
	Boolean termsOfServiceAgreed,
	
	@Schema(description = "개인정보 수집 및 이용 동의 여부")
	Boolean privacyPolicyAgreed,
	
	@Schema(description = "마케팅 수신 동의 여부")
	Boolean marketingAgreed,
	
	@Schema(description = "가입일시", example = "2024-03-21T12:34:56")
	LocalDateTime createdAt
) {
}
