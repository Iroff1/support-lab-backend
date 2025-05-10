package com.iroff.supportlab.application.user.dto;

public record SignUpUserResponse(
    Long id,
    String email,
    String name,
    String phone
) {
}
