package com.iroff.supportlab.domain.common.port.in.exception;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {
    private final ErrorInfo error;

    public DomainException(ErrorInfo error) {
        super(error.getMessage());
        this.error = error;
    }
}
