package com.iroff.supportlab.application.user.service;

import org.springframework.stereotype.Service;

import com.iroff.supportlab.application.user.dto.GetUserInfoResponse;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.user.model.User;
import com.iroff.supportlab.domain.user.port.in.GetUserInfoUseCase;
import com.iroff.supportlab.domain.user.port.in.exception.UserError;
import com.iroff.supportlab.domain.user.port.out.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetUserInfoInteractor implements GetUserInfoUseCase {

	private final UserRepository userRepository;

	@Override
	public GetUserInfoResponse getUserInfo(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new DomainException(UserError.USER_NOT_FOUND));
		return new GetUserInfoResponse(
			user.getName(),
			user.getEmail(),
			user.getPhone(),
			user.getRole(),
			user.getTermsOfServiceAgreed(),
			user.getPrivacyPolicyAgreed(),
			user.getMarketingAgreed(),
			user.getCreatedAt(),
			user.getModifiedAt()
		);
	}
}
