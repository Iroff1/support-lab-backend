package com.iroff.supportlab.application.user.dto;

import java.time.LocalDateTime;

import com.iroff.supportlab.domain.user.model.Role;

public record GetUserInfoResponse(
	String name,
	String email,
	String phone,
	Role role,
	Boolean termsOfServiceAgreed,
	Boolean privacyPolicyAgreed,
	Boolean marketingAgreed,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt
) {
}
