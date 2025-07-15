package com.iroff.supportlab.application.user.service;

import java.time.Duration;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iroff.supportlab.domain.auth.model.vo.VerificationType;
import com.iroff.supportlab.domain.auth.port.in.exception.AuthError;
import com.iroff.supportlab.domain.auth.port.out.VerificationStateRepository;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.common.port.in.exception.ErrorInfo;
import com.iroff.supportlab.domain.user.model.User;
import com.iroff.supportlab.domain.user.port.in.VerifyPasswordUseCase;
import com.iroff.supportlab.domain.user.port.in.exception.UserError;
import com.iroff.supportlab.domain.user.port.out.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerifyPasswordInteractor implements VerifyPasswordUseCase {

	private static final Duration VERIFIED_TTL = Duration.ofMinutes(10); // 10분간 유효
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final VerificationStateRepository verificationStateRepository;

	@Override
	public void verifyPassword(Long userId, String password) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new DomainException(UserError.USER_NOT_FOUND));

		checkCondition(passwordEncoder.matches(password, user.getPassword()), AuthError.INVALID_PASSWORD);

		verificationStateRepository.markedVerifiedByUser(
			VerificationType.USER_INFO_MODIFICATION_VERIFIED,
			userId.toString(),
			userId,
			VERIFIED_TTL
		);
	}

	private void checkCondition(boolean condition, ErrorInfo error) {
		if (!condition) {
			throw new DomainException(error);
		}
	}
}
