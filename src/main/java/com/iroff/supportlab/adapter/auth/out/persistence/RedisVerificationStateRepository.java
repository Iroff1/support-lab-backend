package com.iroff.supportlab.adapter.auth.out.persistence;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.iroff.supportlab.domain.auth.port.out.VerificationStateRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RedisVerificationStateRepository implements VerificationStateRepository {

	private static final String KEY_PREFIX = "verified:";
	private final StringRedisTemplate redis;

	@Override
	public void markedVerified(String phone, Duration ttl) {
		redis.opsForValue().set(KEY_PREFIX + phone, "true", ttl);
	}

	@Override
	public boolean isVerified(String phone) {
		String isVerified = redis.opsForValue().get(KEY_PREFIX + phone);
		return isVerified != null && isVerified.equals("true");
	}

	@Override
	public void remove(String phone) {
		redis.delete(KEY_PREFIX + phone);
	}
}
