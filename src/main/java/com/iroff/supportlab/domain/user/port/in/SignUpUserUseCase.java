package com.iroff.supportlab.domain.user.port.in;

import com.iroff.supportlab.application.user.dto.SignUpUserRequest;
import com.iroff.supportlab.application.user.dto.SignUpUserResponse;
import com.iroff.supportlab.domain.common.port.in.Validator;

public interface SignUpUserUseCase extends Validator<SignUpUserRequest> {
	SignUpUserResponse signUp(SignUpUserRequest request);
}
