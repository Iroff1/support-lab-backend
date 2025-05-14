package com.iroff.supportlab.application.auth.service;

import org.springframework.stereotype.Service;

import com.iroff.supportlab.application.auth.dto.VerifyCodeRequest;
import com.iroff.supportlab.application.auth.dto.VerifyCodeResponse;
import com.iroff.supportlab.application.auth.dto.vo.VerifyCodeResponseStatus;
import com.iroff.supportlab.domain.auth.port.in.VerifyCodeUseCase;
import com.iroff.supportlab.domain.auth.port.out.SmsClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerifyCodeInteractor implements VerifyCodeUseCase {

	private final SmsClient smsClient;

	@Override
	public VerifyCodeResponse verifyCode(VerifyCodeRequest request) {
		String phone = request.phone();
		String code = request.code();
		if (smsClient.verifyCode(phone, code)) {
			return new VerifyCodeResponse(VerifyCodeResponseStatus.SUCCESS);
		}
		return new VerifyCodeResponse(VerifyCodeResponseStatus.FAIL);
	}

	@Override
	public boolean validate(VerifyCodeRequest param) {
		return VerifyCodeUseCase.super.validate(param);
	}
}