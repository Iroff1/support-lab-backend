package com.iroff.supportlab.application.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iroff.supportlab.application.user.dto.UpdatePasswordRequest;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.common.port.in.exception.ErrorInfo;
import com.iroff.supportlab.domain.user.model.User;
import com.iroff.supportlab.domain.user.port.in.UpdatePasswordUseCase;
import com.iroff.supportlab.domain.user.port.in.exception.UserError;
import com.iroff.supportlab.domain.user.port.out.UserRepository;
import com.iroff.supportlab.domain.user.util.PasswordValidator;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdatePasswordInteractor implements UpdatePasswordUseCase {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	@Override
	public void updatePassword(Long userId, UpdatePasswordRequest request) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new DomainException(UserError.USER_NOT_FOUND));

		String oldPassword = request.oldPassword();
		String newPassword = request.newPassword();

		checkCondition(!newPassword.equals(oldPassword), UserError.SAME_PASSWORD_NOT_ALLOWED);
		checkCondition(passwordEncoder.matches(oldPassword, user.getPassword()), UserError.WRONG_PASSWORD);
		PasswordValidator.validatePassword(newPassword);

		user.changePassword(newPassword);
		user.changePassword(passwordEncoder.encode(newPassword));
	}

	void checkCondition(boolean condition, ErrorInfo error) {
		if (!condition) {
			throw new DomainException(error);
		}
	}
}
