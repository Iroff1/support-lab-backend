package com.iroff.supportlab.application.auth.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import com.iroff.supportlab.domain.auth.util.CodeGenerator;

@Service
public class SmsCodeGenerator implements CodeGenerator {
	@Override
	public String generateCode() {
		String code = RandomStringUtils.secure().nextNumeric(6);
		return code;
	}
}
