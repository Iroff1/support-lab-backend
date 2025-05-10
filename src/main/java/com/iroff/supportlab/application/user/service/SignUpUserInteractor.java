package com.iroff.supportlab.application.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iroff.supportlab.adapter.user.out.persistence.UserEntity;
import com.iroff.supportlab.application.user.dto.SignUpUserRequest;
import com.iroff.supportlab.application.user.dto.SignUpUserResponse;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.user.model.Role;
import com.iroff.supportlab.domain.user.model.User;
import com.iroff.supportlab.domain.user.port.in.SignUpUserUseCase;
import com.iroff.supportlab.domain.user.port.in.exception.UserError;
import com.iroff.supportlab.domain.user.port.out.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignUpUserInteractor implements SignUpUserUseCase {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public SignUpUserResponse signUp(SignUpUserRequest request) {
		if (userRepository.existsByEmail(request.email())) {
			throw new DomainException(UserError.EMAIL_ALREADY_EXISTS);
		}

		User user = UserEntity.builder()
			.email(request.email())
			.password(passwordEncoder.encode(request.password()))
			.name(request.name())
			.phone(request.phone())
			.role(Role.USER)
			.build();
		userRepository.save(user);

		return new SignUpUserResponse(
			user.getId(),
			user.getEmail(),
			user.getName(),
			user.getPhone()
		);
	}

	@Override
	public boolean validate(SignUpUserRequest request) {
		// throw new DomainException(UserError.EMAIL_ALREADY_EXISTS); // Use Case 에서는 DomainException을 던짐
		return true;
	}

}
