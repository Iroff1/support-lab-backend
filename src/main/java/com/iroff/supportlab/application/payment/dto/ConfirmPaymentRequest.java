package com.iroff.supportlab.application.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "결제 승인 요청 DTO")
public record ConfirmPaymentRequest(
	@NotBlank(message = "결제 승인 키는 필수입니다.")
	String paymentKey,

	@NotBlank(message = "주문 ID는 필수입니다.")
	String orderId,

	@NotNull(message = "주문 금액은 필수입니다.")
	Integer amount
) {
}
