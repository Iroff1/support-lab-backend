package com.iroff.supportlab.adapter.common.in.web.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.iroff.supportlab.adapter.common.in.web.dto.ResponseDTO;
import com.iroff.supportlab.adapter.common.in.web.dto.vo.ResponseCode;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.common.port.in.exception.ErrorInfo;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(DomainException.class)
	public ResponseEntity<ResponseDTO<ErrorResponse>> handleDomainException(DomainException ex) {
		ErrorInfo error = ex.getError();
		ErrorResponse response = ErrorResponse.of(error.getCode(), error.getDesc(), error.getMessage());
		ResponseDTO<ErrorResponse> dto = new ResponseDTO<>(ResponseCode.BAD_REQUEST, response);
		return ResponseEntity.badRequest().body(dto);
	}

	@ExceptionHandler(APIException.class)
	public ResponseEntity<ResponseDTO<ErrorResponse>> handleApiException(APIException ex) {
		ErrorInfo error = ex.getError();
		ErrorResponse response = ErrorResponse.of(error.getCode(), error.getDesc(), error.getMessage());
		HttpStatus httpStatus = ex.getErrorStatus().getStatusCode(error.getCode());
		ResponseCode code = ResponseCode.getResponseCode(httpStatus);
		ResponseDTO<ErrorResponse> dto = new ResponseDTO<>(code, response);
		return ResponseEntity.status(httpStatus).body(dto);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseDTO<ErrorResponse>> handlerException(Exception ex) {
		String code = "E000";
		String desc = "알 수 없는 오류가 발생했습니다.";
		String message = "[" + code + "] " + desc;
		ResponseCode responseCode = ResponseCode.INTERNAL_SERVER_ERROR;
		ErrorResponse response = ErrorResponse.of(code, desc, message);
		ResponseDTO<ErrorResponse> dto = new ResponseDTO<>(responseCode, response);
		return ResponseEntity.internalServerError().body(dto);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
		MethodArgumentNotValidException ex,
		HttpHeaders headers,
		HttpStatusCode status,
		WebRequest request) {

		String code = "E001";
		String desc = "입력값 검증 실패";
		StringBuilder messageBuilder = new StringBuilder();

		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError)error).getField();
			String errorMessage = error.getDefaultMessage();
			messageBuilder.append(fieldName).append(": ").append(errorMessage).append(", ");
		});

		String message = messageBuilder.toString().replaceAll(", $", "");
		ErrorResponse response = ErrorResponse.of(code, desc, message);
		ResponseDTO<ErrorResponse> dto = new ResponseDTO<>(ResponseCode.BAD_REQUEST, response);
		return ResponseEntity.badRequest().body(dto);
	}
}
