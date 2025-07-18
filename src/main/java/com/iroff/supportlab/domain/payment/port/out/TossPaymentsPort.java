package com.iroff.supportlab.domain.payment.port.out;

import com.iroff.supportlab.application.payment.dto.ConfirmPaymentRequest;
import com.iroff.supportlab.domain.payment.model.Payment;
import reactor.core.publisher.Mono;

public interface TossPaymentsPort {
    Mono<Payment> confirmPayment(ConfirmPaymentRequest confirmPaymentRequest);
}
