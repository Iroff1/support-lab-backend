package com.iroff.supportlab.adapter.auth.in.web.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.iroff.supportlab.adapter.common.in.web.exception.ErrorResponse;
import com.iroff.supportlab.domain.auth.exception.AuthException;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class AuthExceptionHandler {

	private final AuthErrorStatus authErrorStatus;

	@ExceptionHandler(AuthException.class)
	public ResponseEntity<ErrorResponse> handleAuthException(AuthException ex) {
		ErrorResponse response = ErrorResponse.of(
			ex.getError().getCode(),
			ex.getError().getDesc(),
			ex.getError().getMessage()
		);
		return ResponseEntity.status(authErrorStatus.getStatusCode(ex.getError().getCode())).body(response);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
		ErrorResponse response = ErrorResponse.of(
			"E001",
			"아이디 또는 비밀번호가 일치하지 않습니다.",
			"아이디 또는 비밀번호가 일치하지 않습니다."
		);
		return ResponseEntity.status(authErrorStatus.getStatusCode("E001")).body(response);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
		ErrorResponse response = ErrorResponse.of(
			"E004",
			"접근 권한이 없습니다.",
			"해당 리소스에 접근할 권한이 없습니다."
		);
		return ResponseEntity.status(authErrorStatus.getStatusCode("E004")).body(response);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
		ErrorResponse response = ErrorResponse.of(
			"E003",
			"유효하지 않은 토큰입니다.",
			"인증에 실패했습니다."
		);
		return ResponseEntity.status(authErrorStatus.getStatusCode("E003")).body(response);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
		ErrorResponse response = ErrorResponse.of(
			"E005",
			"잘못된 요청입니다.",
			ex.getMessage()
		);
		return ResponseEntity.status(authErrorStatus.getStatusCode("E005")).body(response);
	}
} 
