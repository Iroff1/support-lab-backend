package com.iroff.supportlab.adapter.common.in.web.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
	String code,
	String desc,
	String message,
	LocalDateTime timestamp
) {
	public static ErrorResponse of(
		String code,
		String desc,
		String message) {
		return new ErrorResponse(code, desc, message, LocalDateTime.now());
	}
}