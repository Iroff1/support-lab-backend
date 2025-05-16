package com.iroff.supportlab.application.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iroff.supportlab.adapter.config.global.security.JwtTokenProvider;
import com.iroff.supportlab.application.auth.dto.LoginRequest;
import com.iroff.supportlab.application.auth.dto.LoginResponse;
import com.iroff.supportlab.domain.auth.port.in.LoginUseCase;
import com.iroff.supportlab.domain.user.model.User;
import com.iroff.supportlab.domain.user.port.out.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService implements LoginUseCase {
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;

	@Override
	public LoginResponse login(LoginRequest request) {
		User user = userRepository.findByEmail(request.email())
			.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 이메일 또는 비밀번호입니다."));

		if (!passwordEncoder.matches(request.password(), user.getPassword())) {
			throw new IllegalArgumentException("유효하지 않은 이메일 또는 비밀번호입니다.");
		}

		String token = jwtTokenProvider.createToken(user.getId(), user.getRole().toString());

		return new LoginResponse(token);
	}
}
