package com.iroff.supportlab.adapter.user.in.web.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.iroff.supportlab.adapter.common.in.web.exception.ErrorStatus;
import com.iroff.supportlab.domain.user.port.in.exception.UserError;

@Component
public class UserErrorStatus implements ErrorStatus {
	private final Map<String, HttpStatus> errorMap = new HashMap<>();

	public UserErrorStatus() {
		errorMap.put(UserError.EMAIL_ALREADY_EXISTS.getCode(), HttpStatus.CONFLICT);
		errorMap.put(UserError.PHONE_ALREADY_EXISTS.getCode(), HttpStatus.CONFLICT);
		errorMap.put(UserError.INVALID_PASSWORD_UPPERCASE.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(UserError.INVALID_EMAIL.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(UserError.INVALID_PASSWORD_LENGTH.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(UserError.INVALID_PASSWORD_LOWERCASE.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(UserError.INVALID_PASSWORD_NUMBER.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(UserError.INVALID_PASSWORD_SPECIAL.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(UserError.INVALID_PASSWORD_SEQUENCE.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(UserError.VERIFICATION_FAILED.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(UserError.USER_NOT_EXISTS.getCode(), HttpStatus.NOT_FOUND);
	}

	@Override
	public HttpStatus getStatusCode(String code) {
		return errorMap.getOrDefault(code, HttpStatus.BAD_REQUEST);
	}
}
