package com.iroff.supportlab.domain.payment.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentMethod {
	CARD("카드"),
	VIRTUAL_ACCOUNT("가상계좌"),
	EASY_PAY("간편결제"),
	MOBILE_PHONE("휴대폰"),
	TRANSFER("계좌이체"),
	GIFT_CERTIFICATE("상품권");

	private final String description;

	PaymentMethod(String description) {
		this.description = description;
	}

	@JsonCreator
	public static PaymentMethod from(String value) {
		for (PaymentMethod method : PaymentMethod.values()) {
			if (method.getDescription().equals(value)) {
				return method;
			}
		}
		throw new IllegalArgumentException("알 수 없는 PaymentMethod 값: " + value);
	}

	@JsonValue
	public String getDescription() {
		return description;
	}
}
