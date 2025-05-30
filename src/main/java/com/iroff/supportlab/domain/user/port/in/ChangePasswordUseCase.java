package com.iroff.supportlab.domain.user.port.in;

import com.iroff.supportlab.application.user.dto.ChangePasswordRequest;
import com.iroff.supportlab.application.user.dto.ChangePasswordResponse;

public interface ChangePasswordUseCase {
	ChangePasswordResponse changePassword(ChangePasswordRequest request);
}
