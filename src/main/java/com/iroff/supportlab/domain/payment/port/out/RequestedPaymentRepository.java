package com.iroff.supportlab.domain.payment.port.out;

import java.util.Optional;

import com.iroff.supportlab.domain.payment.model.RequestedPayment;

public interface RequestedPaymentRepository {
	RequestedPayment save(RequestedPayment requestedPayment);

	Optional<RequestedPayment> findByOrderId(String orderId);

	void deleteByOrderId(String orderId);
}
