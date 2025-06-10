package com.iroff.supportlab.application.user.service;

import org.springframework.stereotype.Service;

import com.iroff.supportlab.application.user.dto.CheckEmailExistsResponse;
import com.iroff.supportlab.domain.user.port.in.CheckEmailExistsUseCase;
import com.iroff.supportlab.domain.user.port.out.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckEmailExistsInteractor implements CheckEmailExistsUseCase {

	private final UserRepository userRepository;

	@Override
	public CheckEmailExistsResponse checkEmailExists(String email) {
		return new CheckEmailExistsResponse(userRepository.findByEmail(email).isPresent());
	}
}
