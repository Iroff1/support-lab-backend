package com.iroff.supportlab.domain.payment.port.in;

import com.iroff.supportlab.application.payment.dto.ConfirmPaymentRequest;
import com.iroff.supportlab.domain.payment.model.Payment;

import reactor.core.publisher.Mono;

public interface ConfirmPaymentUseCase {
	Mono<Payment> confirmPayment(Long userId, ConfirmPaymentRequest confirmPaymentRequest);
}
