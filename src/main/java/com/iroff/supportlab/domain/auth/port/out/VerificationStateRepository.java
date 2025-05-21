package com.iroff.supportlab.domain.auth.port.out;

import java.time.Duration;

import com.iroff.supportlab.domain.auth.model.vo.VerificationType;

public interface VerificationStateRepository {
	void markedVerified(VerificationType type, String phone, Duration ttl);

	boolean isVerified(VerificationType type, String phone);

	void remove(VerificationType type, String phone);
}
