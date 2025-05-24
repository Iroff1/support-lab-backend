package com.iroff.supportlab.domain.user.port.in;

import com.iroff.supportlab.application.user.dto.FindEmailRequest;
import com.iroff.supportlab.application.user.dto.FindEmailResponse;
import com.iroff.supportlab.domain.common.port.in.Validator;

public interface FindEmailUseCase extends Validator<FindEmailRequest> {
	FindEmailResponse findEmail(FindEmailRequest request);
}
