package com.iroff.supportlab.application.user.dto;

import java.time.LocalDateTime;

public record SignUpUserResponse(
	Long id,
	String email,
	String name,
	String phone,
	Boolean privacyPolicyAgreed,
	Boolean marketingAgreed,
	LocalDateTime createdAt
) {
}
