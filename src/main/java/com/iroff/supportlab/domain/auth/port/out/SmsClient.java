package com.iroff.supportlab.domain.auth.port.out;

public interface SmsClient {
	void sendCode(String phone, String code);
}
