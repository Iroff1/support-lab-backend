package com.iroff.supportlab.domain.auth.port.in;

import com.iroff.supportlab.application.auth.dto.LoginRequest;
import com.iroff.supportlab.application.auth.dto.LoginResponse;

public interface AuthUseCase {
	LoginResponse login(LoginRequest request);
}
