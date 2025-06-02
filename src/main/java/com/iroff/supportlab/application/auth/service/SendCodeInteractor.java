package com.iroff.supportlab.application.auth.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.iroff.supportlab.application.auth.dto.SendCodeRequest;
import com.iroff.supportlab.domain.auth.model.vo.VerificationType;
import com.iroff.supportlab.domain.auth.port.in.SendCodeUseCase;
import com.iroff.supportlab.domain.auth.port.in.exception.AuthError;
import com.iroff.supportlab.domain.auth.port.out.SmsClient;
import com.iroff.supportlab.domain.auth.port.out.SmsRateLimiter;
import com.iroff.supportlab.domain.auth.port.out.VerificationCodeRepository;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SendCodeInteractor implements SendCodeUseCase {

	private static final int LIMIT = 5;
	private final SmsClient smsClient;
	private final SmsCodeGenerator smsCodeGenerator;
	private final SmsRateLimiter smsRateLimiter;
	private final VerificationCodeRepository verificationCodeRepository;

	@Override
	public void sendCode(SendCodeRequest request, String ip) {
		if (!smsRateLimiter.tryAcquire(ip)) {
			throw new DomainException(AuthError.TOO_MANY_REQUESTS);
		}
		VerificationType type = request.type();
		String phone = request.phone();
		String code = smsCodeGenerator.generateCode();
		smsClient.sendCode(phone, code);
		verificationCodeRepository.save(type, phone, code, Duration.ofMinutes(LIMIT));
	}

	@Override
	public boolean validate(SendCodeRequest param) {
		return SendCodeUseCase.super.validate(param);
	}
}