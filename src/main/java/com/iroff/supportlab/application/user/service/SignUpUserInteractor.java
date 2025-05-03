package com.iroff.supportlab.application.user.service;

import org.springframework.stereotype.Service;

import com.iroff.supportlab.application.user.dto.SignUpUserRequest;
import com.iroff.supportlab.application.user.dto.SignUpUserResponse;
import com.iroff.supportlab.domain.user.port.in.SignUpUserUseCase;

@Service
public class SignUpUserInteractor implements SignUpUserUseCase {
	@Override
	public SignUpUserResponse signUp(SignUpUserRequest request) {
		return null;
	}
}
