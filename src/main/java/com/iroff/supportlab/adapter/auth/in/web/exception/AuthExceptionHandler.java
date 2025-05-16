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

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthExceptionHandler {

	@ExceptionHandler(AuthException.class)
	public ResponseEntity<ErrorResponse> handleAuthException(AuthException ex) {
		ErrorResponse response = ErrorResponse.of(
			ex.getError().getCode(),
			ex.getError().getDesc(),
			ex.getError().getMessage()
		);
		return ResponseEntity.status(AuthErrorStatus.INVALID_CREDENTIALS.getStatusCode(ex.getError().getCode()))
			.body(response);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
		ErrorResponse response = ErrorResponse.of(
			AuthErrorStatus.INVALID_CREDENTIALS.getCode(),
			AuthErrorStatus.INVALID_CREDENTIALS.getMessage(),
			"아이디 또는 비밀번호가 일치하지 않습니다."
		);
		return ResponseEntity.status(AuthErrorStatus.INVALID_CREDENTIALS.getHttpStatus()).body(response);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
		ErrorResponse response = ErrorResponse.of(
			AuthErrorStatus.ACCESS_DENIED.getCode(),
			AuthErrorStatus.ACCESS_DENIED.getMessage(),
			"해당 리소스에 접근할 권한이 없습니다."
		);
		return ResponseEntity.status(AuthErrorStatus.ACCESS_DENIED.getHttpStatus()).body(response);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
		ErrorResponse response = ErrorResponse.of(
			AuthErrorStatus.INVALID_TOKEN.getCode(),
			AuthErrorStatus.INVALID_TOKEN.getMessage(),
			"인증에 실패했습니다."
		);
		return ResponseEntity.status(AuthErrorStatus.INVALID_TOKEN.getHttpStatus()).body(response);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
		ErrorResponse response = ErrorResponse.of(
			AuthErrorStatus.INVALID_ARGUMENT.getCode(),
			AuthErrorStatus.INVALID_ARGUMENT.getMessage(),
			ex.getMessage()
		);
		return ResponseEntity.status(AuthErrorStatus.INVALID_ARGUMENT.getHttpStatus()).body(response);
	}
} 
