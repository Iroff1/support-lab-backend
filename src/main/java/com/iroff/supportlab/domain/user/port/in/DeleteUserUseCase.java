package com.iroff.supportlab.domain.user.port.in;

import com.iroff.supportlab.application.user.dto.DeleteUserRequest;

public interface DeleteUserUseCase {
	void deleteUser(Long userId, DeleteUserRequest request);
}
