package com.iroff.supportlab.application.auth.dto;

public record LoginRequest(
	String email,
	String password
) {
}
