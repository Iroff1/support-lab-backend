package com.iroff.supportlab.adapter.auth.in.web.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.iroff.supportlab.adapter.common.in.web.exception.ErrorStatus;

@Component
public class AuthErrorStatus implements ErrorStatus {
	private final Map<String, HttpStatus> errorMap = new HashMap<>();

	public AuthErrorStatus() {
		errorMap.put("A001", HttpStatus.BAD_REQUEST);
		errorMap.put("A002", HttpStatus.BAD_REQUEST);
		errorMap.put("A003", HttpStatus.BAD_REQUEST);
		errorMap.put("A004", HttpStatus.TOO_MANY_REQUESTS);
	}

	@Override
	public HttpStatus getStatusCode(String code) {
		return errorMap.getOrDefault(code, HttpStatus.BAD_REQUEST);
	}
}
