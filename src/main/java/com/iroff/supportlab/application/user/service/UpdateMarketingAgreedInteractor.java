package com.iroff.supportlab.application.user.service;

import org.springframework.stereotype.Service;

import com.iroff.supportlab.application.user.dto.UpdateMarketingAgreedRequest;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.common.port.in.exception.ErrorInfo;
import com.iroff.supportlab.domain.user.model.User;
import com.iroff.supportlab.domain.user.port.in.UpdateMarketingAgreedUseCase;
import com.iroff.supportlab.domain.user.port.in.exception.UserError;
import com.iroff.supportlab.domain.user.port.out.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateMarketingAgreedInteractor implements UpdateMarketingAgreedUseCase {

	private final UserRepository userRepository;

	@Transactional
	@Override
	public void updateMarketingAgreed(Long userId, UpdateMarketingAgreedRequest request) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new DomainException(UserError.USER_NOT_FOUND));

		boolean marketingAgreed = request.marketingAgreed();
		user.changeMarketingAgreed(marketingAgreed);
	}
}