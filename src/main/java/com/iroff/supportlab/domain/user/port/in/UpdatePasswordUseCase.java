package com.iroff.supportlab.domain.user.port.in;

import com.iroff.supportlab.application.user.dto.UpdatePasswordRequest;

public interface UpdatePasswordUseCase {
	void updatePassword(Long userId, UpdatePasswordRequest request);
}
