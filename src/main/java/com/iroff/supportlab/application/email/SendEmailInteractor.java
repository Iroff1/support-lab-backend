package com.iroff.supportlab.application.email;

import org.springframework.stereotype.Service;

import com.iroff.supportlab.domain.email.port.in.SendEmailUseCase;
import com.iroff.supportlab.domain.email.port.out.SendEmailPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SendEmailInteractor implements SendEmailUseCase {

	private final SendEmailPort sendEmailPort;

	@Override
	public void sendEmail(String to, String subject, String content) {
		sendEmailPort.sendEmail(to, subject, content);
	}
} 