package com.iroff.supportlab.domain.payment.model;

public enum PaymentStatus {
	READY("준비"),
	IN_PROGRESS("진행중"),
	WAITING_FOR_DEPOSIT("입금 대기"),
	DONE("결제 완료"),
	CANCELED("취소"),
	PARTIAL_CANCELED("부분 취소"),
	ABORTED("중단"),
	EXPIRED("만료");

	private final String description;

	PaymentStatus(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
