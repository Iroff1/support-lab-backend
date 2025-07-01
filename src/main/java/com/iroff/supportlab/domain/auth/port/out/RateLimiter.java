package com.iroff.supportlab.domain.auth.port.out;

public interface RateLimiter {
	boolean tryAcquire(String key);
}
