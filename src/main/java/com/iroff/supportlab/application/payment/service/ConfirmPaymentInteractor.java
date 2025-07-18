package com.iroff.supportlab.application.payment.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iroff.supportlab.application.payment.dto.ConfirmPaymentRequest;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.common.port.in.exception.ErrorInfo;
import com.iroff.supportlab.domain.payment.model.Payment;
import com.iroff.supportlab.domain.payment.model.RequestedPayment;
import com.iroff.supportlab.domain.payment.port.in.ConfirmPaymentUseCase;
import com.iroff.supportlab.domain.payment.port.in.exception.PaymentError;
import com.iroff.supportlab.domain.payment.port.out.PaymentRepository;
import com.iroff.supportlab.domain.payment.port.out.RequestedPaymentRepository;
import com.iroff.supportlab.domain.payment.port.out.TossPaymentsPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConfirmPaymentInteractor implements ConfirmPaymentUseCase {

	private final TossPaymentsPort tossPaymentsPort;
	private final PaymentRepository paymentRepository;
	private final RequestedPaymentRepository requestedPaymentRepository;

	@Override
	@Transactional
	public Mono<Payment> confirmPayment(Long userId, ConfirmPaymentRequest confirmPaymentRequest) {
		String orderId = confirmPaymentRequest.orderId();
		Integer amount = confirmPaymentRequest.amount();

		log.info("결제 승인 시작. 주문 ID: {}, 사용자 ID: {}", orderId, userId);

		RequestedPayment requestedPayment = requestedPaymentRepository.findByOrderId(orderId)
			.orElseThrow(() -> {
				log.error("결제 승인 실패: 임시 저장된 주문 정보를 찾을 수 없습니다. 주문 ID: {}, 사용자 ID: {}", orderId, userId);
				return new DomainException(PaymentError.ORDER_NOT_FOUND);
			});
		log.info("임시 결제 정보 조회 완료. 주문 ID: {}, 임시 저장된 사용자 ID: {}", orderId, requestedPayment.getUserId());

		// 1. 금액 위변조 검증
		checkCondition(Objects.equals(requestedPayment.getAmount(), amount), PaymentError.MANIPULATED_PAYMENT,
			() -> log.error("결제 승인 실패: 결제 정보가 위변조되었습니다. 주문 ID: {}, 사용자 ID: {}", orderId, userId));
		log.info("결제 금액 검증 완료. 주문 ID: {}, 사용자 ID: {}", orderId, userId);

		// 2. 사용자 ID 일치 여부 검증
		checkCondition(Objects.equals(requestedPayment.getUserId(), userId),
			PaymentError.UNAUTHORIZED_PAYMENT_CONFIRMATION,
			() -> log.error(
				"결제 승인 실패: 요청 사용자 ID와 임시 저장된 사용자 ID가 일치하지 않습니다. 주문 ID: {}, 요청 사용자 ID: {}, 임시 저장된 사용자 ID: {}",
				orderId, userId, requestedPayment.getUserId()));
		log.info("사용자 ID 검증 완료. 주문 ID: {}, 사용자 ID: {}", orderId, userId);

		return tossPaymentsPort.confirmPayment(confirmPaymentRequest)
			.flatMap(confirmedPayment -> {
				log.info("토스페이먼츠 결제 승인 API 호출 성공. Payment Key: {}, 주문 ID: {}, 사용자 ID: {}",
					confirmedPayment.getPaymentKey(), confirmedPayment.getOrderId(), userId);
				Payment paymentToSave = addUserIdToPayment(confirmedPayment, userId);
				Payment payment = paymentRepository.save(paymentToSave);
				log.info("최종 결제 정보 저장 완료. 주문 ID: {}, 사용자 ID: {}", payment.getOrderId(), payment.getUserId());
				requestedPaymentRepository.deleteByOrderId(orderId);
				log.info("임시 결제 정보 삭제 완료. 주문 ID: {}, 사용자 ID: {}", orderId, userId);
				return Mono.just(payment);
			});
	}

	private void checkCondition(boolean condition, ErrorInfo error, Runnable logger) {
		if (!condition) {
			logger.run();
			throw new DomainException(error);
		}
	}
	
	private Payment addUserIdToPayment(Payment payment, Long userId) {
		return Payment.builder()
			.paymentId(payment.getPaymentId())
			.version(payment.getVersion())
			.paymentKey(payment.getPaymentKey())
			.type(payment.getType())
			.orderId(payment.getOrderId())
			.orderName(payment.getOrderName())
			.mid(payment.getMid())
			.currency(payment.getCurrency())
			.method(payment.getMethod())
			.totalAmount(payment.getTotalAmount())
			.balanceAmount(payment.getBalanceAmount())
			.status(payment.getStatus())
			.requestedAt(payment.getRequestedAt())
			.approvedAt(payment.getApprovedAt())
			.useEscrow(payment.getUseEscrow())
			.lastTransactionKey(payment.getLastTransactionKey())
			.suppliedAmount(payment.getSuppliedAmount())
			.vat(payment.getVat())
			.cultureExpense(payment.getCultureExpense())
			.taxFreeAmount(payment.getTaxFreeAmount())
			.taxExemptionAmount(payment.getTaxExemptionAmount())
			.userId(userId) // userId 설정
			.card(payment.getCard())
			.virtualAccount(payment.getVirtualAccount())
			.mobilePhone(payment.getMobilePhone())
			.giftCertificate(payment.getGiftCertificate())
			.transfer(payment.getTransfer())
			.receipt(payment.getReceipt())
			.easyPay(payment.getEasyPay())
			.failure(payment.getFailure())
			.cashReceipt(payment.getCashReceipt())
			.discount(payment.getDiscount())
			.build();
	}
}
