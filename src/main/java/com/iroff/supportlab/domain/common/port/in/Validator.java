package com.iroff.supportlab.domain.common.port.in;

public interface Validator<T> {
	default boolean validate(T param) {
		return true;
	}
}
