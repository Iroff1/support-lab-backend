package com.iroff.supportlab.adapter.payment.in.web.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.iroff.supportlab.adapter.common.in.web.exception.ErrorStatus;
import com.iroff.supportlab.domain.payment.port.in.exception.PaymentError;

@Component
public class PaymentErrorStatus implements ErrorStatus {
	private final Map<String, HttpStatus> errorMap = new HashMap<>();

	public PaymentErrorStatus() {
		errorMap.put(PaymentError.ORDER_NOT_FOUND.getCode(), HttpStatus.NOT_FOUND);
		errorMap.put(PaymentError.MANIPULATED_PAYMENT.getCode(), HttpStatus.BAD_REQUEST);
		errorMap.put(PaymentError.UNAUTHORIZED_PAYMENT_CONFIRMATION.getCode(), HttpStatus.UNAUTHORIZED);
		errorMap.put(PaymentError.FAIL_TO_PAY.getCode(), HttpStatus.BAD_REQUEST);
	}

	@Override
	public HttpStatus getStatusCode(String code) {
		return errorMap.getOrDefault(code, HttpStatus.BAD_REQUEST);
	}
}