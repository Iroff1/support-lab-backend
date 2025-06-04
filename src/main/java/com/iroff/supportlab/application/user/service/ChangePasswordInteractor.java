package com.iroff.supportlab.application.user.service;

import static com.iroff.supportlab.domain.user.util.PasswordValidator.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iroff.supportlab.application.user.dto.ChangePasswordRequest;
import com.iroff.supportlab.domain.auth.model.vo.VerificationType;
import com.iroff.supportlab.domain.auth.port.in.exception.AuthError;
import com.iroff.supportlab.domain.auth.port.out.VerificationCodeRepository;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.user.model.User;
import com.iroff.supportlab.domain.user.port.in.ChangePasswordUseCase;
import com.iroff.supportlab.domain.user.port.in.exception.UserError;
import com.iroff.supportlab.domain.user.port.out.UserRepository;

import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChangePasswordInteractor implements ChangePasswordUseCase {

	private final UserRepository userRepository;
	private final VerificationCodeRepository verificationCodeRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	@Override
	public void changePassword(ChangePasswordRequest request) {
		String token = request.token();
		String password = request.password();
		validatePassword(password);

		Long userId = verificationCodeRepository.find(VerificationType.RESET_PASSWORD, token)
			.orElseThrow(() -> new DomainException(AuthError.EXPIRED_TOKEN))
			.transform(Long::parseLong);
		verificationCodeRepository.remove(VerificationType.RESET_PASSWORD, token);

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new DomainException(UserError.USER_NOT_FOUND));

		String newPassword = passwordEncoder.encode(password);
		try {
			user.changePassword(newPassword);
		} catch (OptimisticLockException e) {
			throw new DomainException(UserError.PASSWORD_ALREADY_CHANGED);
		}
	}
}
