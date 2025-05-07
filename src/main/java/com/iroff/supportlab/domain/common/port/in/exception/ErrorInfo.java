package com.iroff.supportlab.domain.common.port.in.exception;

public interface ErrorInfo {
	String getDesc();

	default String getMessage() {
		return "[" + getCode() + "] " + getDesc();
	}

	String getCode();

	default DomainException toException() {
		return new DomainException(this);
	}
}
