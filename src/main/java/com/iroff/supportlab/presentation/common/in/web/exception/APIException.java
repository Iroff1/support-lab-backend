package com.iroff.supportlab.presentation.common.in.web.exception;

import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.common.port.in.exception.ErrorInfo;

import lombok.Getter;

@Getter
public class APIException extends RuntimeException {
	private ErrorInfo error;
	private ErrorStatus errorStatus;

	public APIException(DomainException ex, ErrorStatus errorStatus) {
		super(ex.getError().getMessage());
		this.error = ex.getError();
		this.errorStatus = errorStatus;
	}
}
