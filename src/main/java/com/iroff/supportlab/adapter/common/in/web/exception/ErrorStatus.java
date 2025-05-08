package com.iroff.supportlab.adapter.common.in.web.exception;

import org.springframework.http.HttpStatus;

public interface ErrorStatus {
	HttpStatus getStatusCode(String code);
}
