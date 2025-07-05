package com.iroff.supportlab.domain.user.port.in;

import com.iroff.supportlab.application.user.dto.UpdateMarketingAgreedRequest;

public interface UpdateMarketingAgreedUseCase {
	void updateMarketingAgreed(Long userId, UpdateMarketingAgreedRequest request);
}