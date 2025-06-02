package com.iroff.supportlab.domain.user.port.in;

import com.iroff.supportlab.application.user.dto.RequestChangePasswordRequest;
import com.iroff.supportlab.application.user.dto.RequestChangePasswordResponse;

public interface RequestChangePasswordUseCase {
	RequestChangePasswordResponse requestChangePassword(RequestChangePasswordRequest request);
}
