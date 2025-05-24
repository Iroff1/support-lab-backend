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

	private final StringRedisTemplate redis;

	@Override
	public void markedVerified(VerificationType type, String phone, Duration ttl) {
		redis.opsForValue().set(type.getValue() + phone, "true", ttl);
	}

	@Override
	public boolean isVerified(VerificationType type, String phone) {
		String isVerified = redis.opsForValue().get(type.getValue() + phone);
		return isVerified != null && isVerified.equals("true");
	}

	@Override
	public void remove(VerificationType type, String phone) {
		redis.delete(type.getValue() + phone);
	}
}
