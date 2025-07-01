package com.iroff.supportlab.application.auth.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.iroff.supportlab.adapter.auth.out.config.SmsProperties;
import com.iroff.supportlab.application.auth.dto.SendCodeEmailRequest;
import com.iroff.supportlab.domain.auth.model.vo.VerificationType;
import com.iroff.supportlab.domain.auth.port.in.SendCodeEmailUseCase;
import com.iroff.supportlab.domain.auth.port.in.exception.AuthError;
import com.iroff.supportlab.domain.auth.port.out.RateLimiter;
import com.iroff.supportlab.domain.auth.port.out.VerificationCodeRepository;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.email.port.out.SendEmailPort;
import com.iroff.supportlab.framework.config.email.EmailConfig;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SendCodeEmailInteractor implements SendCodeEmailUseCase {

	private final static int LIMIT = 5;
	private final RateLimiter rateLimiter;
	private final VerifyCodeGenerator verifyCodeGenerator;
	private final VerificationCodeRepository verificationCodeRepository;
	private final SendEmailPort sendEmailPort;
	private final SmsProperties smsProperties;
	private final EmailConfig emailConfig;

	@Override
	public void sendCodeEmail(SendCodeEmailRequest request, String ip) {
		if (!rateLimiter.tryAcquire(ip)) {
			throw new DomainException(AuthError.TOO_MANY_REQUESTS);
		}
		VerificationType type = VerificationType.VERIFY_EMAIL_CODE;
		String email = request.email();
		String code = verifyCodeGenerator.generateCode();
		String message = code + " " + smsProperties.getAuthTemplate();
		sendEmailPort.sendEmail(email, emailConfig.getCodeSubject(), message);
		verificationCodeRepository.save(type, email, code, Duration.ofMinutes(LIMIT));
	}
}
