package com.iroff.supportlab.adapter.auth.out.persistence;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.iroff.supportlab.domain.auth.model.vo.VerificationType;
import com.iroff.supportlab.domain.auth.port.out.VerificationStateRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RedisVerificationStateRepository implements VerificationStateRepository {

	private static final String VERIFIED_VALUE = "true";
	private static final String KEY_PREFIX = "verification-state:";
	private final StringRedisTemplate redis;

	private String generateKey(VerificationType type, String target) {
		return KEY_PREFIX + type.getValue() + ":" + target;
	}

	private String generateKey(VerificationType type, String target, Long userId) {
		return KEY_PREFIX + type.getValue() + ":" + target + ":" + userId;
	}

	@Override
	public void markedVerified(VerificationType type, String phone, Duration ttl) {
		redis.opsForValue().set(generateKey(type, phone), VERIFIED_VALUE, ttl);
	}

	@Override
	public void markedVerifiedByUser(VerificationType type, String phone, Long userId, Duration ttl) {
		redis.opsForValue().set(generateKey(type, phone, userId), VERIFIED_VALUE, ttl);
	}

	@Override
	public boolean isVerified(VerificationType type, String phone) {
		String isVerified = redis.opsForValue().get(generateKey(type, phone));
		return VERIFIED_VALUE.equals(isVerified);
	}

	@Override
	public boolean isVerifiedByUser(VerificationType type, String phone, Long userId) {
		String isVerified = redis.opsForValue().get(generateKey(type, phone, userId));
		return VERIFIED_VALUE.equals(isVerified);
	}

	@Override
	public void remove(VerificationType type, String phone) {
		redis.delete(generateKey(type, phone));
	}

	@Override
	public void removeByUser(VerificationType type, String phone, Long userId) {
		redis.delete(generateKey(type, phone, userId));
	}
}