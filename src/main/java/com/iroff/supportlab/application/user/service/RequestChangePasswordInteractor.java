package com.iroff.supportlab.application.user.service;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.iroff.supportlab.application.user.dto.RequestChangePasswordRequest;
import com.iroff.supportlab.application.user.dto.RequestChangePasswordResponse;
import com.iroff.supportlab.domain.auth.model.vo.VerificationType;
import com.iroff.supportlab.domain.auth.port.in.exception.AuthError;
import com.iroff.supportlab.domain.auth.port.out.VerificationCodeRepository;
import com.iroff.supportlab.domain.auth.port.out.VerificationStateRepository;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.common.port.in.exception.ErrorInfo;
import com.iroff.supportlab.domain.user.model.User;
import com.iroff.supportlab.domain.user.port.in.RequestChangePasswordUseCase;
import com.iroff.supportlab.domain.user.port.out.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestChangePasswordInteractor implements RequestChangePasswordUseCase {

	private final UserRepository userRepository;
	private final VerificationCodeRepository verificationCodeRepository;
	private final VerificationStateRepository verificationStateRepository;

	@Override
	public RequestChangePasswordResponse requestChangePassword(RequestChangePasswordRequest request) {
		String email = request.email();
		String name = request.name();
		String phone = request.phone();
		Optional<User> user = userRepository.findByPhone(phone);

		checkCondition(verificationStateRepository.isVerified(VerificationType.FIND_PASSWORD_VERIFIED, phone),
			AuthError.VERIFY_CODE_FAILED);
		verificationStateRepository.remove(VerificationType.FIND_PASSWORD_VERIFIED, phone);

		checkCondition(user.isPresent() && user.get().getName().equals(name) && user.get().getEmail().equals(email),
			AuthError.INVALID_AUTHORIZATION);

		String token = UUID.randomUUID().toString();
		verificationCodeRepository.save(VerificationType.RESET_PASSWORD, token, user.get().getId().toString(),
			Duration.ofMinutes(10));
		return new RequestChangePasswordResponse(token);
	}

	private void checkCondition(boolean condition, ErrorInfo error) {
		if (!condition) {
			throw new DomainException(error);
		}
	}
}
