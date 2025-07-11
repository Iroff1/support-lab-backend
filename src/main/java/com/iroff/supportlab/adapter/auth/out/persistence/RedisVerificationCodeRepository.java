package com.iroff.supportlab.adapter.auth.out.persistence;

import java.time.Duration;
import java.util.Optional;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.iroff.supportlab.domain.auth.model.vo.VerificationType;
import com.iroff.supportlab.domain.auth.port.out.VerificationCodeRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RedisVerificationCodeRepository implements VerificationCodeRepository {

	private static final String KEY_PREFIX = "verification-code:";
	private final StringRedisTemplate redis;

	private String generateKey(VerificationType type, String target) {
		return KEY_PREFIX + type.getValue() + ":" + target;
	}

	private String generateKey(VerificationType type, String target, Long userId) {
		return KEY_PREFIX + type.getValue() + ":" + target + ":" + userId;
	}

	@Override
	public void save(VerificationType type, String phone, String code, Duration ttl) {
		redis.opsForValue().set(generateKey(type, phone), code, ttl);
	}

	@Override
	public void saveByUserId(VerificationType type, String phone, Long userId, String code, Duration ttl) {
		redis.opsForValue().set(generateKey(type, phone, userId), code, ttl);
	}

	@Override
	public Optional<String> find(VerificationType type, String phone) {
		String code = redis.opsForValue().get(generateKey(type, phone));
		return Optional.ofNullable(code);
	}

	@Override
	public Optional<String> findByUser(VerificationType type, String phone, Long userId) {
		String code = redis.opsForValue().get(generateKey(type, phone, userId));
		return Optional.ofNullable(code);
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