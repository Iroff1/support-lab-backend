package com.iroff.supportlab.domain.auth.port.in;

import com.iroff.supportlab.application.auth.dto.SendCodeEmailRequest;

public interface SendCodeEmailUseCase {
	void sendCodeEmail(SendCodeEmailRequest request, String ip);
}
