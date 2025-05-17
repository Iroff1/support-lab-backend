package com.iroff.supportlab.application.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iroff.supportlab.adapter.user.out.persistence.UserEntity;
import com.iroff.supportlab.application.user.dto.SignUpUserRequest;
import com.iroff.supportlab.application.user.dto.SignUpUserResponse;
import com.iroff.supportlab.domain.auth.port.out.VerificationStateRepository;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.user.model.Role;
import com.iroff.supportlab.domain.user.port.in.SignUpUserUseCase;
import com.iroff.supportlab.domain.user.port.in.exception.UserError;
import com.iroff.supportlab.domain.user.port.out.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignUpUserInteractor implements SignUpUserUseCase {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final VerificationStateRepository verificationStateRepository;

	@Transactional
	@Override
	public SignUpUserResponse signUp(SignUpUserRequest request) {
		if (userRepository.existsByEmail(request.email())) {
			throw new DomainException(UserError.EMAIL_ALREADY_EXISTS);
		} else if (userRepository.existsByPhone(request.phone())) {
			throw new DomainException(UserError.PHONE_ALREADY_EXISTS);
		} else if (!verificationStateRepository.isVerified(request.phone())) {
			throw new DomainException(UserError.VERIFICATION_FAILED);
		} else if (request.privacyPolicyAgreed() == null || !request.privacyPolicyAgreed()) {
			throw new DomainException(UserError.PRIVACY_POLICY_AGREE_IS_NECCESARY);
		} else if (request.marketingAgreed() == null) {
			throw new DomainException(UserError.INVALID_MARKETING_AGREE);
		}

		validatePassword(request.password());

		verificationStateRepository.remove(request.phone());

		UserEntity user = UserEntity.builder()
			.email(request.email())
			.password(passwordEncoder.encode(request.password()))
			.name(request.name())
			.phone(request.phone())
			.role(Role.USER)
			.privacyPolicyAgreed(request.privacyPolicyAgreed())
			.marketingAgreed(request.marketingAgreed())
			.build();
		try {
			userRepository.save(user);
		} catch (Exception e) {
			throw new DomainException(UserError.EMAIL_ALREADY_EXISTS);
		}

		return new SignUpUserResponse(
			user.getId(),
			user.getEmail(),
			user.getName(),
			user.getPhone(),
			user.getPrivacyPolicyAgreed(),
			user.getMarketingAgreed(),
			user.getCreatedAt()
		);
	}

	private void validatePassword(String password) {
		if (password == null || password.length() < 8) {
			throw new DomainException(UserError.INVALID_PASSWORD_LENGTH);
		}

		if (!password.matches(".*[A-Z].*")) {
			throw new DomainException(UserError.INVALID_PASSWORD_UPPERCASE);
		}

		if (!password.matches(".*[a-z].*")) {
			throw new DomainException(UserError.INVALID_PASSWORD_LOWERCASE);
		}

		if (!password.matches(".*[0-9].*")) {
			throw new DomainException(UserError.INVALID_PASSWORD_NUMBER);
		}

		if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
			throw new DomainException(UserError.INVALID_PASSWORD_SPECIAL);
		}

		// 연속된 문자나 반복된 문자 검사
		for (int i = 0; i < password.length() - 2; i++) {
			char c1 = password.charAt(i);
			char c2 = password.charAt(i + 1);
			char c3 = password.charAt(i + 2);

			// 연속된 숫자 검사 (예: 123, 234)
			if (Character.isDigit(c1) && Character.isDigit(c2) && Character.isDigit(c3)) {
				if (c2 == c1 + 1 && c3 == c2 + 1) {
					throw new DomainException(UserError.INVALID_PASSWORD_SEQUENCE);
				}
			}

			// 연속된 문자 검사 (예: abc, bcd)
			if (Character.isLetter(c1) && Character.isLetter(c2) && Character.isLetter(c3)) {
				if (c2 == c1 + 1 && c3 == c2 + 1) {
					throw new DomainException(UserError.INVALID_PASSWORD_SEQUENCE);
				}
			}

			// 반복된 문자 검사 (예: aaa, 111)
			if (c1 == c2 && c2 == c3) {
				throw new DomainException(UserError.INVALID_PASSWORD_SEQUENCE);
			}
		}
	}

	@Override
	public boolean validate(SignUpUserRequest request) {
		// throw new DomainException(UserError.EMAIL_ALREADY_EXISTS); // Use Case 에서는 DomainException을 던짐
		return true;
	}

}
