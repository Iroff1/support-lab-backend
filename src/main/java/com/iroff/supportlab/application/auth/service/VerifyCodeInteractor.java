package com.iroff.supportlab.application.auth.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.iroff.supportlab.application.auth.dto.VerifyCodeRequest;
import com.iroff.supportlab.application.auth.dto.VerifyCodeResponse;
import com.iroff.supportlab.application.auth.dto.vo.VerifyCodeResponseStatus;
import com.iroff.supportlab.domain.auth.model.vo.VerificationType;
import com.iroff.supportlab.domain.auth.port.in.VerifyCodeUseCase;
import com.iroff.supportlab.domain.auth.port.in.exception.AuthError;
import com.iroff.supportlab.domain.auth.port.out.VerificationCodeRepository;
import com.iroff.supportlab.domain.auth.port.out.VerificationStateRepository;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerifyCodeInteractor implements VerifyCodeUseCase {

	private final VerificationCodeRepository codeRepository;
	private final VerificationStateRepository stateRepository;

	@Override
	public VerifyCodeResponse verifyCode(VerifyCodeRequest request) {
		VerificationType type = request.type();
		String phone = request.phone();
		String code = request.code();

		String savedCode = codeRepository.find(type, phone)
			.orElseThrow(() -> new DomainException(AuthError.CODE_NOT_EXISTS));

		if (!savedCode.equals(code)) {
			throw new DomainException(AuthError.VERIFY_CODE_FAILED);
		}
		codeRepository.remove(type, phone);

		VerificationType verifiedType = switch (type) {
			case SIGN_UP_CODE -> VerificationType.SIGN_UP_VERIFIED;
			case FIND_EMAIL_CODE -> VerificationType.FIND_EMAIL_VERIFIED;
			case FIND_PASSWORD_CODE -> VerificationType.FIND_PASSWORD_VERIFIED;
			default -> throw new DomainException(AuthError.INVALID_REQUEST);
		};
		stateRepository.markedVerified(verifiedType, phone, Duration.ofMinutes(10));
		return new VerifyCodeResponse(VerifyCodeResponseStatus.SUCCESS);
	}

	@Override
	public boolean validate(VerifyCodeRequest param) {
		return VerifyCodeUseCase.super.validate(param);
	}
}