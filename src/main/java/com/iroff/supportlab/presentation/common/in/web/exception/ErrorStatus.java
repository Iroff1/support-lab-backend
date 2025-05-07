package com.iroff.supportlab.presentation.common.in.web.exception;

import org.springframework.http.HttpStatus;

public interface ErrorStatus {
	HttpStatus getStatusCode(String code);
}
