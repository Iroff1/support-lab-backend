package com.iroff.supportlab.domain.auth.port.in;

import com.iroff.supportlab.application.auth.dto.VerifyCodeEmailRequest;
import com.iroff.supportlab.application.auth.dto.VerifyCodeResponse;

public interface VerifyCodeEmailUseCase {
	VerifyCodeResponse verifyCodeEmail(VerifyCodeEmailRequest request);
}
