package com.iroff.supportlab.domain.auth.port.out;

import java.time.Duration;
import java.util.Optional;

import com.iroff.supportlab.domain.auth.model.vo.VerificationType;

public interface VerificationCodeRepository {
	void save(VerificationType type, String phone, String code, Duration ttl);

	void saveByUserId(VerificationType type, String phone, Long userId, String code, Duration ttl);

	Optional<String> find(VerificationType type, String phone);

	Optional<String> findByUser(VerificationType type, String phone, Long userId);

	void remove(VerificationType type, String phone);

	void removeByUser(VerificationType type, String phone, Long userId);
}
