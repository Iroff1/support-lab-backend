package com.iroff.supportlab.adapter.common.in.web.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.common.port.in.exception.ErrorInfo;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(DomainException.class)
	public ResponseEntity<ErrorResponse> handleDomainException(DomainException ex) {
		ErrorInfo error = ex.getError();
		ErrorResponse response = ErrorResponse.of(error.getCode(), error.getDesc(), error.getMessage());
		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler(APIException.class)
	public ResponseEntity<ErrorResponse> handleApiException(APIException ex) {
		ErrorInfo error = ex.getError();
		ErrorResponse response = ErrorResponse.of(error.getCode(), error.getDesc(), error.getMessage());
		return ResponseEntity.status(ex.getErrorStatus().getStatusCode(error.getCode())).body(response);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handlerException(Exception ex) {
		String code = "E000";
		String desc = "알 수 없는 오류가 발생했습니다.";
		String message = "[" + code + "] " + desc;
		ErrorResponse response = ErrorResponse.of(code, desc, message);
		return ResponseEntity.internalServerError().body(response);
	}
}
