package com.iroff.supportlab.domain.user.port.in;

import com.iroff.supportlab.application.user.dto.UpdatePhoneNumberRequest;

public interface UpdatePhoneNumberUseCase {
	void updatePhoneNumber(Long userId, UpdatePhoneNumberRequest request);
}