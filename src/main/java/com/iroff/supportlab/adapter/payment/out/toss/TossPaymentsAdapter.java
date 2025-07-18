package com.iroff.supportlab.adapter.payment.out.toss;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.iroff.supportlab.application.payment.dto.ConfirmPaymentRequest;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.payment.model.Payment;
import com.iroff.supportlab.domain.payment.port.in.exception.PaymentError;
import com.iroff.supportlab.domain.payment.port.out.TossPaymentsPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class TossPaymentsAdapter implements TossPaymentsPort {

	private final WebClient tossWebClient;

	@Override
	public Mono<Payment> confirmPayment(ConfirmPaymentRequest request) {
		log.info("토스페이먼츠 결제 승인 API 호출 시작. 주문 ID: {}", request.orderId());
		return tossWebClient.post()
			.uri("/v1/payments/confirm")
			.bodyValue(request)
			.retrieve()
			.bodyToMono(Payment.class)
			.doOnSuccess(payment -> log.info("토스페이먼츠 API 응답 성공. Payment Key: {}", payment.getPaymentKey()))
			.doOnError(error -> {
				log.error("토스페이먼츠 API 호출 중 오류 발생.", error);
				throw new DomainException(PaymentError.FAIL_TO_PAY);
			});
	}
}