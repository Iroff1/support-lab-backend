package com.iroff.supportlab.application.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.iroff.supportlab.adapter.common.util.EmailMaskingUtil;
import com.iroff.supportlab.application.user.dto.FindEmailRequest;
import com.iroff.supportlab.application.user.dto.FindEmailResponse;
import com.iroff.supportlab.domain.auth.model.vo.VerificationType;
import com.iroff.supportlab.domain.auth.port.in.exception.AuthError;
import com.iroff.supportlab.domain.auth.port.out.VerificationStateRepository;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.user.model.User;
import com.iroff.supportlab.domain.user.port.in.FindEmailUseCase;
import com.iroff.supportlab.domain.user.port.out.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FindEmailInteractor implements FindEmailUseCase {

	private final UserRepository userRepository;
	private final VerificationStateRepository verificationStateRepository;

	@Override
	public FindEmailResponse findEmail(FindEmailRequest request) {
		String name = request.name();
		String phone = request.phone();

		if (!verificationStateRepository.isVerified(VerificationType.FIND_EMAIL_VERIFIED, phone)) {
			throw new DomainException(AuthError.VERIFY_CODE_FAILED);
		}

		Optional<User> user = userRepository.findByPhone(phone);
		String email = null;
		if (user.isPresent() && user.get().getName().equals(name)) {
			email = EmailMaskingUtil.maskMiddle(user.get().getEmail());
		}
		return new FindEmailResponse(email);
	}
}
