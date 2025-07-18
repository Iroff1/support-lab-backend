package com.iroff.supportlab.adapter.payment.out.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.iroff.supportlab.domain.payment.model.Payment;
import com.iroff.supportlab.domain.payment.port.out.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

	private final PaymentJpaRepository paymentJpaRepository;
	private final PaymentMapper paymentMapper;

	@Override
	public Payment save(Payment payment) {
		PaymentEntity entity = paymentMapper.mapToEntity(payment);
		PaymentEntity savedEntity = paymentJpaRepository.save(entity);
		return paymentMapper.mapToDomain(savedEntity);
	}

	@Override
	public Optional<Payment> findByOrderId(String orderId) {
		return paymentJpaRepository.findByOrderId(orderId)
			.map(paymentMapper::mapToDomain);
	}
}
