package com.iroff.supportlab.application.auth.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.iroff.supportlab.application.auth.dto.VerifyCodeRequest;
import com.iroff.supportlab.application.auth.dto.VerifyCodeResponse;
import com.iroff.supportlab.application.auth.dto.vo.VerifyCodeResponseStatus;
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
		String phone = request.phone();
		String code = request.code();

		String savedCode = codeRepository.find(phone)
			.orElseThrow(() -> new DomainException(AuthError.CODE_NOT_EXISTS));

		if (!savedCode.equals(code)) {
			throw new DomainException(AuthError.VERIFY_CODE_FAILED);
		}
		codeRepository.remove(phone);
		stateRepository.markedVerified(phone, Duration.ofMinutes(10));
		return new VerifyCodeResponse(VerifyCodeResponseStatus.SUCCESS);
	}

	@Override
	public boolean validate(VerifyCodeRequest param) {
		return VerifyCodeUseCase.super.validate(param);
	}
}