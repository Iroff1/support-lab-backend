package com.iroff.supportlab.domain.auth.port.out;

import java.time.Duration;

public interface VerificationStateRepository {
	void markedVerified(String phone, Duration ttl);

	boolean isVerified(String phone);

	void remove(String phone);
}
