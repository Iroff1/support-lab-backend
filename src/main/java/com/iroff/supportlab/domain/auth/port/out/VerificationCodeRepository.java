package com.iroff.supportlab.domain.auth.port.out;

import java.time.Duration;
import java.util.Optional;

import com.iroff.supportlab.domain.auth.model.vo.VerificationType;

public interface VerificationCodeRepository {
	void save(VerificationType type, String code, String phone, Duration ttl);

	Optional<String> find(VerificationType type, String phone);

	void remove(VerificationType type, String phone);
}
