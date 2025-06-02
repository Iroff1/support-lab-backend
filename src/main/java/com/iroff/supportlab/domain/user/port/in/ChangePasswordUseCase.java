package com.iroff.supportlab.domain.user.port.in;

import com.iroff.supportlab.application.user.dto.ChangePasswordRequest;

public interface ChangePasswordUseCase {
	void changePassword(ChangePasswordRequest request);
}
