package com.iroff.supportlab.domain.auth.port.in;

import com.iroff.supportlab.application.auth.dto.SendCodeRequest;
import com.iroff.supportlab.domain.common.port.in.Validator;

public interface SendCodeUseCase extends Validator<SendCodeRequest> {
	void sendCode(SendCodeRequest request, String ip, Long userId);
}
