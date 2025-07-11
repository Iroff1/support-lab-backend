package com.iroff.supportlab.application.auth.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.iroff.supportlab.application.auth.dto.SendCodeRequest;
import com.iroff.supportlab.domain.auth.model.vo.VerificationType;
import com.iroff.supportlab.domain.auth.port.in.SendCodeUseCase;
import com.iroff.supportlab.domain.auth.port.in.exception.AuthError;
import com.iroff.supportlab.domain.auth.port.out.RateLimiter;
import com.iroff.supportlab.domain.auth.port.out.SmsClient;
import com.iroff.supportlab.domain.auth.port.out.VerificationCodeRepository;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SendCodeInteractor implements SendCodeUseCase {

	private static final int MAX_TRY_LIMIT = 5;
	private final SmsClient smsClient;
	private final VerifyCodeGenerator verifyCodeGenerator;
	private final RateLimiter rateLimiter;
	private final VerificationCodeRepository verificationCodeRepository;

	@Override
	public void sendCode(SendCodeRequest request, String ip, Long userId) {
		if (!rateLimiter.tryAcquire(ip)) {
			throw new DomainException(AuthError.TOO_MANY_REQUESTS);
		}
		VerificationType type = request.type();
		String phone = request.phone();
		String code = verifyCodeGenerator.generateCode();
		smsClient.sendCode(phone, code);
		if (userId == null && type != VerificationType.UPDATE_PHONE_CODE) {
			verificationCodeRepository.save(type, phone, code, Duration.ofMinutes(MAX_TRY_LIMIT));
		} else if (userId != null && type == VerificationType.UPDATE_PHONE_CODE) {
			verificationCodeRepository.saveByUserId(type, phone, userId, code,
				Duration.ofMinutes(MAX_TRY_LIMIT));
		} else {
			throw new DomainException(AuthError.INVALID_REQUEST);
		}
	}

	@Override
	public boolean validate(SendCodeRequest param) {
		return SendCodeUseCase.super.validate(param);
	}
}