package com.iroff.supportlab.domain.auth.port.in;

import com.iroff.supportlab.application.auth.dto.VerifyCodeRequest;
import com.iroff.supportlab.application.auth.dto.VerifyCodeResponse;
import com.iroff.supportlab.domain.common.port.in.Validator;

public interface VerifyCodeUseCase extends Validator<VerifyCodeRequest> {
	VerifyCodeResponse verifyCode(VerifyCodeRequest request, Long userId);
}
