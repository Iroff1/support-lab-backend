package com.iroff.supportlab.adapter.email.in.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.iroff.supportlab.adapter.common.in.web.exception.ErrorStatus;
import com.iroff.supportlab.domain.email.port.in.exception.EmailError;

public class EmailErrorStatus implements ErrorStatus {

	private final Map<String, HttpStatus> errorMap = new HashMap<>();

	public EmailErrorStatus() {
		errorMap.put(EmailError.SEND_EMAIL_FAILED.getCode(), HttpStatus.BAD_REQUEST);
	}

	@Override
	public HttpStatus getStatusCode(String code) {
		return errorMap.getOrDefault(code, HttpStatus.BAD_REQUEST);
	}
}
