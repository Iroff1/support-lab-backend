package com.iroff.supportlab.adapter.user.in.web.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.iroff.supportlab.adapter.common.in.web.exception.ErrorStatus;
import com.iroff.supportlab.domain.user.port.in.exception.UserError;

public class UserErrorStatus implements ErrorStatus {
	private final Map<String, HttpStatus> errorMap = new HashMap<>();

	public UserErrorStatus() {
		errorMap.put(UserError.EMAIL_ALREADY_EXISTS.getCode(), HttpStatus.CONFLICT);
		errorMap.put(UserError.PHONE_ALREADY_EXISTS.getCode(), HttpStatus.CONFLICT);
		errorMap.put(UserError.INVALID_NAME.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(UserError.INVALID_EMAIL.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(UserError.INVALID_PASSWORD_LENGTH.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(UserError.INVALID_PASSWORD_UPPERCASE.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(UserError.INVALID_PASSWORD_LOWERCASE.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(UserError.INVALID_PASSWORD_NUMBER.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(UserError.INVALID_PASSWORD_SPECIAL.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(UserError.INVALID_PASSWORD_SEQUENCE.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(UserError.INVALID_MARKETING_AGREE.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(UserError.TERMS_OF_SERVICE_AGREE_IS_NECCESSARY.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(UserError.PRIVACY_POLICY_AGREE_IS_NECCESARY.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(UserError.VERIFICATION_FAILED.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(UserError.USER_NOT_FOUND.getCode(), HttpStatus.NOT_FOUND);
		errorMap.put(UserError.PASSWORD_ALREADY_CHANGED.getCode(), HttpStatus.CONFLICT);
		errorMap.put(UserError.DELETE_USER_FAILED.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(UserError.UPDATE_PASSWORD_FAILED.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(UserError.WRONG_PASSWORD.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(UserError.SAME_NAME_NOT_ALLOWED.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(UserError.SAME_PASSWORD_NOT_ALLOWED.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(UserError.SAME_PHONE_NUMBER_NOT_ALLOWED.getCode(), HttpStatus.BAD_REQUEST);
	}

	@Override
	public HttpStatus getStatusCode(String code) {
		return errorMap.getOrDefault(code, HttpStatus.BAD_REQUEST);
	}
}
