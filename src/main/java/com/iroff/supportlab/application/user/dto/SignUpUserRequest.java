package com.iroff.supportlab.application.user.dto;

public record SignUpUserRequest(
	String email,
	String password,
	String name,
	String phone
) {
}
