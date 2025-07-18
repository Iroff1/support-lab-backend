package com.iroff.supportlab.adapter.auth.in.web.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.iroff.supportlab.adapter.common.in.web.exception.ErrorStatus;
import com.iroff.supportlab.domain.auth.port.in.exception.AuthError;

@Component
public class AuthErrorStatus implements ErrorStatus {
	private final Map<String, HttpStatus> errorMap = new HashMap<>();

	public AuthErrorStatus() {
		errorMap.put(AuthError.CODE_NOT_EXISTS.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(AuthError.SEND_CODE_FAILED.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(AuthError.VERIFY_CODE_FAILED.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(AuthError.TOO_MANY_REQUESTS.getCode(), HttpStatus.TOO_MANY_REQUESTS);
		errorMap.put(AuthError.NEED_AUTHORIZATION.getCode(), HttpStatus.UNAUTHORIZED);
		errorMap.put(AuthError.INVALID_AUTHORIZATION.getCode(), HttpStatus.UNAUTHORIZED);
		errorMap.put(AuthError.EXPIRED_TOKEN.getCode(), HttpStatus.UNAUTHORIZED);
		errorMap.put(AuthError.TOKEN_EXPIRED.getCode(), HttpStatus.FORBIDDEN);
		errorMap.put(AuthError.INVALID_REQUEST.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(AuthError.INVALID_PASSWORD.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(AuthError.LOGOUT_FAILED.getCode(), HttpStatus.BAD_REQUEST);
	}

	@Override
	public HttpStatus getStatusCode(String code) {
		return errorMap.getOrDefault(code, HttpStatus.BAD_REQUEST);
	}
}
