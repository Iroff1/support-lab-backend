package com.iroff.supportlab.application.auth.service;

import org.springframework.stereotype.Service;

import com.iroff.supportlab.application.auth.dto.SendCodeRequest;
import com.iroff.supportlab.domain.auth.port.in.SendCodeUseCase;
import com.iroff.supportlab.domain.auth.port.out.SmsClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SendCodeInteractor implements SendCodeUseCase {

	private final SmsClient smsClient;

	@Override
	public void sendCode(SendCodeRequest request) {
		String phone = request.phone();
		smsClient.sendCode(phone);
	}

	@Override
	public boolean validate(SendCodeRequest param) {
		return SendCodeUseCase.super.validate(param);
	}
}