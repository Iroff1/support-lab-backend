package com.iroff.supportlab.domain.auth.port.out;

import java.time.Duration;

import com.iroff.supportlab.domain.auth.model.vo.VerificationType;

public interface VerificationStateRepository {
	void markedVerified(VerificationType type, String phone, Duration ttl);

	void markedVerifiedByUser(VerificationType type, String phone, Long userId, Duration ttl);

	boolean isVerified(VerificationType type, String phone);

	boolean isVerifiedByUser(VerificationType type, String phone, Long userId);

	void remove(VerificationType type, String phone);

	void removeByUser(VerificationType type, String phone, Long userId);
}
