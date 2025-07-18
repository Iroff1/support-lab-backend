package com.iroff.supportlab.domain.payment.port.in;

import com.iroff.supportlab.application.payment.dto.RequestPaymentRequest;
import com.iroff.supportlab.domain.payment.model.RequestedPayment;

public interface RequestPaymentUseCase {
	RequestedPayment requestPayment(Long userId, RequestPaymentRequest requestPaymentRequest);
}
