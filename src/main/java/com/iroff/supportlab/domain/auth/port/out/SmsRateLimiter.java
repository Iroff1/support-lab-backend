package com.iroff.supportlab.domain.auth.port.out;

public interface SmsRateLimiter {
	boolean tryAcquire(String key);
}
