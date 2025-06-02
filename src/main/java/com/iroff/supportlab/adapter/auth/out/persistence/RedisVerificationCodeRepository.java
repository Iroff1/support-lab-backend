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

	private final StringRedisTemplate redis;

	@Override
	public void save(VerificationType type, String phone, String code, Duration ttl) {
		redis.opsForValue().set(type.getValue() + phone, code, ttl);
	}

	@Override
	public Optional<String> find(VerificationType type, String phone) {
		String code = redis.opsForValue().get(type.getValue() + phone);
		return Optional.ofNullable(code);
	}

	@Override
	public void remove(VerificationType type, String phone) {
		redis.delete(type.getValue() + phone);
	}
}
