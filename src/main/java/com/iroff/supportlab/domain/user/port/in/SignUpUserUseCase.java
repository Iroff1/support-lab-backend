package com.iroff.supportlab.domain.user.port.in;

import com.iroff.supportlab.application.user.dto.SignUpUserRequest;
import com.iroff.supportlab.application.user.dto.SignUpUserResponse;

public interface SignUpUserUseCase {
	SignUpUserResponse signUp(SignUpUserRequest request);
}
