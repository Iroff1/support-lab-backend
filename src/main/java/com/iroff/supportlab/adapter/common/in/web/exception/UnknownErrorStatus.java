package com.iroff.supportlab.adapter.common.in.web.exception;

import org.springframework.http.HttpStatus;

public class UnknownErrorStatus implements ErrorStatus {
	@Override
	public HttpStatus getStatusCode(String code) {
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}
}
