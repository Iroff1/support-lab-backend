package com.iroff.supportlab.application.user.service;

import static com.iroff.supportlab.domain.user.util.PasswordValidator.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iroff.supportlab.application.user.dto.SignUpUserRequest;
import com.iroff.supportlab.application.user.dto.SignUpUserResponse;
import com.iroff.supportlab.domain.auth.model.vo.VerificationType;
import com.iroff.supportlab.domain.auth.port.out.VerificationStateRepository;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.common.port.in.exception.ErrorInfo;
import com.iroff.supportlab.domain.user.model.Role;
import com.iroff.supportlab.domain.user.model.User;
import com.iroff.supportlab.domain.user.port.in.SignUpUserUseCase;
import com.iroff.supportlab.domain.user.port.in.exception.UserError;
import com.iroff.supportlab.domain.user.port.out.UserRepository;
import com.iroff.supportlab.domain.user.util.NameValidator;

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
		checkCondition(!userRepository.existsByEmail(request.email()), UserError.EMAIL_ALREADY_EXISTS);
		checkCondition(!userRepository.existsByPhone(request.phone()), UserError.PHONE_ALREADY_EXISTS);
		checkCondition(NameValidator.isValidName(request.name()), UserError.INVALID_NAME);
		checkCondition(verificationStateRepository.isVerified(VerificationType.SIGN_UP_VERIFIED, request.phone()),
			UserError.VERIFICATION_FAILED);
		checkCondition(request.termsOfServiceAgreed() != null && request.termsOfServiceAgreed(),
			UserError.TERMS_OF_SERVICE_AGREE_IS_NECCESSARY);
		checkCondition(request.privacyPolicyAgreed() != null && request.privacyPolicyAgreed(),
			UserError.PRIVACY_POLICY_AGREE_IS_NECCESARY);
		checkCondition(request.marketingAgreed() != null, UserError.INVALID_MARKETING_AGREE);

		validatePassword(request.password());

		verificationStateRepository.remove(VerificationType.SIGN_UP_VERIFIED, request.phone());

		String encodedPassword = passwordEncoder.encode(request.password());
		User user = User.builder()
			.email(request.email())
			.password(passwordEncoder.encode(request.password()))
			.name(request.name())
			.phone(request.phone())
			.role(Role.USER)
			.active(true)
			.termsOfServiceAgreed(request.termsOfServiceAgreed())
			.privacyPolicyAgreed(request.privacyPolicyAgreed())
			.marketingAgreed(request.marketingAgreed())
			.build();
		try {
			user = userRepository.save(user);
		} catch (Exception e) {
			throw new DomainException(UserError.EMAIL_ALREADY_EXISTS);
		}

		return new SignUpUserResponse(
			user.getId(),
			user.getEmail(),
			user.getName(),
			user.getPhone(),
			user.getTermsOfServiceAgreed(),
			user.getPrivacyPolicyAgreed(),
			user.getMarketingAgreed(),
			user.getCreatedAt());
	}

	@Override
	public boolean validate(SignUpUserRequest request) {
		// throw new DomainException(UserError.EMAIL_ALREADY_EXISTS); // Use Case 에서는 DomainException을 던짐
		return true;
	}

	private void checkCondition(boolean condition, ErrorInfo error) {
		if (!condition) {
			throw new DomainException(error);
		}
	}
}
