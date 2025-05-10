package com.iroff.supportlab.adapter.user.in.web.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.iroff.supportlab.adapter.common.in.web.exception.ErrorStatus;

@Component
public class UserErrorStatus implements ErrorStatus {
	private final Map<String, HttpStatus> errorMap = new HashMap<>();

	public UserErrorStatus() {
		errorMap.put("U001", HttpStatus.BAD_REQUEST);
		errorMap.put("U002", HttpStatus.BAD_REQUEST);
		errorMap.put("U003", HttpStatus.BAD_REQUEST);
		errorMap.put("U004", HttpStatus.BAD_REQUEST);
		errorMap.put("U005", HttpStatus.BAD_REQUEST);
		errorMap.put("U006", HttpStatus.BAD_REQUEST);
		errorMap.put("U007", HttpStatus.BAD_REQUEST);
	}

	@Override
	public HttpStatus getStatusCode(String code) {
		return errorMap.getOrDefault(code, HttpStatus.BAD_REQUEST);
	}
}
