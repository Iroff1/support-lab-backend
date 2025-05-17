package com.iroff.supportlab.domain.auth.exception;

import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.common.port.in.exception.ErrorInfo;

public class AuthException extends DomainException {
	public AuthException(ErrorInfo error) {
		super(error);
	}
} 
