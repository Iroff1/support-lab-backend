package com.iroff.supportlab.application.payment.dto;

import com.iroff.supportlab.domain.payment.model.RequestedPayment;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "결제 요청 응답 DTO")
public record RequestPaymentResponse(
	@Schema(description = "주문 ID")
	String orderId,

	@Schema(description = "주문 품목(주문명)")
	String orderName,

	@Schema(description = "주문 금액")
	Integer amount
) {
	public static RequestPaymentResponse from(RequestedPayment payment) {
		return new RequestPaymentResponse(
			payment.getOrderId(),
			payment.getOrderName(),
			payment.getAmount()
		);
	}
}