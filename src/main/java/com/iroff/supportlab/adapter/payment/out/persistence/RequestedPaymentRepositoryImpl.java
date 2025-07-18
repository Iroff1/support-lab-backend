package com.iroff.supportlab.adapter.payment.out.persistence;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.iroff.supportlab.adapter.payment.out.persistence.redis.RequestedPaymentRedisEntity;
import com.iroff.supportlab.adapter.payment.out.persistence.redis.RequestedPaymentRedisRepository;
import com.iroff.supportlab.domain.payment.model.RequestedPayment;
import com.iroff.supportlab.domain.payment.port.out.RequestedPaymentRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RequestedPaymentRepositoryImpl implements RequestedPaymentRepository {

	private final RequestedPaymentRedisRepository redisRepository;

	private RequestedPaymentRedisEntity mapToRedisEntity(RequestedPayment domain) {
		return RequestedPaymentRedisEntity.builder()
			.orderId(domain.getOrderId())
			.orderName(domain.getOrderName())
			.amount(domain.getAmount())
			.userId(domain.getUserId())
			.createdAt(LocalDateTime.now())
			.expiration(300L) // 5분 (300초) TTL 설정
			.build();
	}

	private RequestedPayment mapToDomainEntity(RequestedPaymentRedisEntity redisEntity) {
		return RequestedPayment.builder()
			.requestedPaymentId(null) // Redis 엔티티에는 없으므로 null로 설정
			.orderId(redisEntity.getOrderId())
			.orderName(redisEntity.getOrderName())
			.amount(redisEntity.getAmount())
			.userId(redisEntity.getUserId())
			.build();
	}

	@Override
	public RequestedPayment save(RequestedPayment requestedPayment) {
		RequestedPaymentRedisEntity redisEntity = mapToRedisEntity(requestedPayment);
		RequestedPaymentRedisEntity savedEntity = redisRepository.save(redisEntity);
		return mapToDomainEntity(savedEntity);
	}

	@Override
	public Optional<RequestedPayment> findByOrderId(String orderId) {
		return redisRepository.findByOrderId(orderId).map(this::mapToDomainEntity);
	}

	@Override
	public void deleteByOrderId(String orderId) {
		redisRepository.deleteByOrderId(orderId);
	}
}
