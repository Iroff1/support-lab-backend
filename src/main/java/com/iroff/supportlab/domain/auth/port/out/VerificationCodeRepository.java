package com.iroff.supportlab.domain.auth.port.out;

import java.time.Duration;
import java.util.Optional;

public interface VerificationCodeRepository {
	void save(String phone, String code, Duration ttl);

	Optional<String> find(String phone);

	void remove(String phone);
}
