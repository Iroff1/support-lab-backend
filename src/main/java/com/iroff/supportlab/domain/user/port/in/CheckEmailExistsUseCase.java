package com.iroff.supportlab.domain.user.port.in;

import com.iroff.supportlab.application.user.dto.CheckEmailExistsResponse;

public interface CheckEmailExistsUseCase {
	CheckEmailExistsResponse checkEmailExists(String email);
}
