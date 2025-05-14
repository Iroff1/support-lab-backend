package com.iroff.supportlab.adapter.auth.out.persistence;

import java.time.Duration;
import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.iroff.supportlab.domain.auth.port.out.VerificationCodeRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RedisVerificationCodeRepository implements VerificationCodeRepository {

	private static final String KEY_PREFIX = "otp:";
	private final RedisTemplate<String, String> redis;

	@Override
	public void save(String phone, String code, Duration ttl) {
		redis.opsForValue().set(KEY_PREFIX + phone, code, ttl);
	}

	@Override
	public Optional<String> find(String phone) {
		String code = redis.opsForValue().get(KEY_PREFIX + phone);
		return Optional.ofNullable(code);
	}

	@Override
	public void remove(String phone) {
		redis.delete(KEY_PREFIX + phone);
	}
}
