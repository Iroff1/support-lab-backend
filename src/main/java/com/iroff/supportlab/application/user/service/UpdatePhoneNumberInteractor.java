package com.iroff.supportlab.application.user.service;

import org.springframework.stereotype.Service;

import com.iroff.supportlab.application.user.dto.UpdatePhoneNumberRequest;
import com.iroff.supportlab.domain.auth.model.vo.VerificationType;
import com.iroff.supportlab.domain.auth.port.in.exception.AuthError;
import com.iroff.supportlab.domain.auth.port.out.VerificationStateRepository;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.common.port.in.exception.ErrorInfo;
import com.iroff.supportlab.domain.user.model.User;
import com.iroff.supportlab.domain.user.port.in.UpdatePhoneNumberUseCase;
import com.iroff.supportlab.domain.user.port.in.exception.UserError;
import com.iroff.supportlab.domain.user.port.out.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdatePhoneNumberInteractor implements UpdatePhoneNumberUseCase {

	private final UserRepository userRepository;
	private final VerificationStateRepository verificationStateRepository;

	@Transactional
	@Override
	public void updatePhoneNumber(Long userId, UpdatePhoneNumberRequest request) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new DomainException(UserError.USER_NOT_FOUND));

		String newPhone = request.newPhone();
		checkCondition(verificationStateRepository.isVerifiedByUser(VerificationType.UPDATE_PHONE_VERIFIED, newPhone,
				userId),
			AuthError.INVALID_AUTHORIZATION);
		checkCondition(!user.getPhone().equals(newPhone), UserError.SAME_PHONE_NUMBER_NOT_ALLOWED);
		checkCondition(!userRepository.existsByPhone(newPhone), UserError.PHONE_ALREADY_EXISTS);

		verificationStateRepository.remove(VerificationType.UPDATE_PHONE_VERIFIED, newPhone);
		user.changePhone(newPhone);
		userRepository.save(user);
	}

	void checkCondition(boolean condition, ErrorInfo error) {
		if (!condition) {
			throw new DomainException(error);
		}
	}
}