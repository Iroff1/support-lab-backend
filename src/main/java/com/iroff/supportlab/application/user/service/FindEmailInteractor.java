package com.iroff.supportlab.application.user.service;

import org.springframework.stereotype.Service;

import com.iroff.supportlab.application.user.dto.FindEmailRequest;
import com.iroff.supportlab.application.user.dto.FindEmailResponse;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.user.model.User;
import com.iroff.supportlab.domain.user.port.in.FindEmailUseCase;
import com.iroff.supportlab.domain.user.port.in.exception.UserError;
import com.iroff.supportlab.domain.user.port.out.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FindEmailInteractor implements FindEmailUseCase {

	private final UserRepository userRepository;

	@Override
	public FindEmailResponse findEmail(FindEmailRequest request) {
		String name = request.name();
		String phone = request.phone();

		User user = userRepository.findByNameAndPhone(name, phone)
			.orElseThrow(() -> new DomainException(UserError.USER_NOT_EXISTS));

		return new FindEmailResponse(user.getEmail());
	}
}
