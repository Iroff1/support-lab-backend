package com.iroff.supportlab.adapter.auth.out.persistence;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.iroff.supportlab.adapter.auth.out.config.SmsProperties;
import com.iroff.supportlab.domain.auth.port.out.SmsRateLimiter;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisRateLimiter implements SmsRateLimiter {

	private static final String KEY_PREFIX = "sms:rate:";
	private final SmsProperties smsProperties;
	private final StringRedisTemplate redisTemplate;

	@Override
	public boolean tryAcquire(String key) {
		String redisKey = KEY_PREFIX + key;
		Long limit = smsProperties.getLimitAmount();
		Duration window = Duration.ofMinutes(smsProperties.getLimitDurationMinutes());
		Long count = redisTemplate.opsForValue().increment(redisKey);
		if (count == 1) {
			redisTemplate.expire(redisKey, window);
		}
		return count <= limit;
	}
}
