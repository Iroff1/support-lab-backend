package com.iroff.supportlab.domain.user.port.in;

import com.iroff.supportlab.application.user.dto.GetUserInfoResponse;

public interface GetUserInfoUseCase {
	GetUserInfoResponse getUserInfo(Long userId);
}
