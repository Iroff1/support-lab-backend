package com.iroff.supportlab.domain.user.port.in;

import com.iroff.supportlab.application.user.dto.UpdateNameRequest;

public interface UpdateNameUseCase {
	void updateName(Long userId, UpdateNameRequest request);
}