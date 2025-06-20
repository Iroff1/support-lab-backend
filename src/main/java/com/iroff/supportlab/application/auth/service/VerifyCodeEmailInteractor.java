package com.iroff.supportlab.application.auth.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.iroff.supportlab.application.auth.dto.VerifyCodeEmailRequest;
import com.iroff.supportlab.application.auth.dto.VerifyCodeResponse;
import com.iroff.supportlab.application.auth.dto.vo.VerifyCodeResponseStatus;
import com.iroff.supportlab.domain.auth.model.vo.VerificationType;
import com.iroff.supportlab.domain.auth.port.in.VerifyCodeEmailUseCase;
import com.iroff.supportlab.domain.auth.port.in.exception.AuthError;
import com.iroff.supportlab.domain.auth.port.out.VerificationCodeRepository;
import com.iroff.supportlab.domain.auth.port.out.VerificationStateRepository;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerifyCodeEmailInteractor implements VerifyCodeEmailUseCase {

	private final VerificationCodeRepository codeRepository;
	private final VerificationStateRepository stateRepository;

	@Override
	public VerifyCodeResponse verifyCodeEmail(VerifyCodeEmailRequest request) {
		VerificationType type = VerificationType.VERIFY_EMAIL_CODE;
		String email = request.email();
		String code = request.code();

		String savedCode = codeRepository.find(type, email)
			.orElseThrow(() -> new DomainException(AuthError.CODE_NOT_EXISTS));

		if (!savedCode.equals(code)) {
			throw new DomainException(AuthError.VERIFY_CODE_FAILED);
		}
		codeRepository.remove(type, email);

		VerificationType verifiedType = VerificationType.VERIFY_EMAIL_VERIFIED;
		stateRepository.markedVerified(verifiedType, email, Duration.ofMinutes(10));
		return new VerifyCodeResponse(VerifyCodeResponseStatus.SUCCESS);
	}
}
