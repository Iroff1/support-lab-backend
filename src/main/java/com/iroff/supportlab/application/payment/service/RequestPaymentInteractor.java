package com.iroff.supportlab.application.payment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iroff.supportlab.application.payment.dto.RequestPaymentRequest;
import com.iroff.supportlab.domain.payment.model.RequestedPayment;
import com.iroff.supportlab.domain.payment.port.in.RequestPaymentUseCase;
import com.iroff.supportlab.domain.payment.port.out.RequestedPaymentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RequestPaymentInteractor implements RequestPaymentUseCase {

	private final RequestedPaymentRepository requestedPaymentRepository;

	@Override
	public RequestedPayment requestPayment(Long userId, RequestPaymentRequest requestPaymentRequest) {
		log.info("결제 요청 시작. 주문 ID: {}, 사용자 ID: {}", requestPaymentRequest.orderId(), userId);
		RequestedPayment requestedPayment = RequestedPayment.builder()
			.orderId(requestPaymentRequest.orderId())
			.orderName(requestPaymentRequest.orderName())
			.amount(requestPaymentRequest.amount())
			.userId(userId) // userId 설정
			.build();
		RequestedPayment savedPayment = requestedPaymentRepository.save(requestedPayment);
		log.info("결제 요청 정보 임시 저장 완료. 주문 ID: {}, 사용자 ID: {}", savedPayment.getOrderId(), savedPayment.getUserId());
		return savedPayment;
	}
}