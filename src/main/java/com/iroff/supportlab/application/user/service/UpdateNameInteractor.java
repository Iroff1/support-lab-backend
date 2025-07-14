package com.iroff.supportlab.application.user.service;

import org.springframework.stereotype.Service;

import com.iroff.supportlab.application.user.dto.UpdateNameRequest;
import com.iroff.supportlab.domain.auth.model.vo.VerificationType;
import com.iroff.supportlab.domain.auth.port.in.exception.AuthError;
import com.iroff.supportlab.domain.auth.port.out.VerificationStateRepository;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.common.port.in.exception.ErrorInfo;
import com.iroff.supportlab.domain.user.model.User;
import com.iroff.supportlab.domain.user.port.in.UpdateNameUseCase;
import com.iroff.supportlab.domain.user.port.in.exception.UserError;
import com.iroff.supportlab.domain.user.port.out.UserRepository;
import com.iroff.supportlab.domain.user.util.NameValidator;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateNameInteractor implements UpdateNameUseCase {

	private final UserRepository userRepository;
	private final VerificationStateRepository stateRepository;

	@Transactional
	@Override
	public void updateName(Long userId, UpdateNameRequest request) {
		checkCondition(
			stateRepository.isVerifiedByUser(VerificationType.USER_INFO_MODIFICATION_VERIFIED, userId.toString(),
				userId),
			AuthError.INVALID_AUTHORIZATION);

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new DomainException(UserError.USER_NOT_FOUND));

		String newName = request.newName();
		checkCondition(!user.getName().equals(newName), UserError.SAME_NAME_NOT_ALLOWED);
		checkCondition(NameValidator.isValidName(newName), UserError.INVALID_NAME);
		user.changeName(newName);
		userRepository.save(user);
	}

	void checkCondition(boolean condition, ErrorInfo error) {
		if (!condition) {
			throw new DomainException(error);
		}
	}
}
