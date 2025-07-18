package com.iroff.supportlab.domain.payment.port.in.exception;

import com.iroff.supportlab.domain.common.port.in.exception.ErrorInfo;

public enum PaymentError implements ErrorInfo {

	ORDER_NOT_FOUND("결제 정보가 존재하지 않습니다."),
	MANIPULATED_PAYMENT("결제 정보가 올바르지 않습니다."),
	UNAUTHORIZED_PAYMENT_CONFIRMATION("결제 승인 권한이 없습니다."), // 새로운 에러 코드 추가
	FAIL_TO_PAY("결제에 실패했습니다.");

	private final String desc;

	PaymentError(String desc) {
		this.desc = desc;
	}

	@Override
	public String getCode() {
		return String.format("P%03d", ordinal() + 1);
	}

	@Override
	public String getDesc() {
		return desc;
	}
}