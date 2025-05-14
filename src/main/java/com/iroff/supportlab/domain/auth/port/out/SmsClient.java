package com.iroff.supportlab.domain.auth.port.out;

public interface SmsClient {
	void sendCode(String phone);

	boolean verifyCode(String phone, String code);
}
