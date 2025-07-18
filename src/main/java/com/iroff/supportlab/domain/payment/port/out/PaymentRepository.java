package com.iroff.supportlab.domain.payment.port.out;

import java.util.Optional;

import com.iroff.supportlab.domain.payment.model.Payment;

public interface PaymentRepository {
	Payment save(Payment payment);

	Optional<Payment> findByOrderId(String orderId);
}