package com.iroff.supportlab.adapter.auth.in.web.exception;

import org.springframework.http.HttpStatus;

import com.iroff.supportlab.adapter.common.in.web.exception.ErrorStatus;

public enum AuthErrorStatus implements ErrorStatus {
	INVALID_CREDENTIALS("E001", "아이디 또는 비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
	TOKEN_EXPIRED("E002", "토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
	INVALID_TOKEN("E003", "유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
	ACCESS_DENIED("E004", "접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
	INVALID_ARGUMENT("E005", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST);

	private final String code;
	private final String message;
	private final HttpStatus httpStatus;

	AuthErrorStatus(String code, String message, HttpStatus httpStatus) {
		this.code = code;
		this.message = message;
		this.httpStatus = httpStatus;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	@Override
	public HttpStatus getStatusCode(String code) {
		for (AuthErrorStatus status : values()) {
			if (status.getCode().equals(code)) {
				return status.getHttpStatus();
			}
		}
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}
}
