package com.iroff.supportlab.domain.auth.port.in.exception;

import com.iroff.supportlab.domain.common.port.in.exception.ErrorInfo;

public enum AuthError implements ErrorInfo {
	SEND_CODE_FAILED("인증 코드 전송에 실패했습니다."),
	VERIFY_CODE_FAILED("인증 코드 검증에 실패했습니다."),
	CODE_NOT_EXISTS("인증 코드가 만료되었거나 발급되지 않았습니다. 다시 발급해주세요."),
	TOO_MANY_REQUESTS("너무 많은 요청이 발생했습니다.");

	private final String desc;

	AuthError(String desc) {
		this.desc = desc;
	}

	@Override
	public String getCode() {
		return String.format("U%03d", ordinal() + 1);
	}

	@Override
	public String getDesc() {
		return desc;
	}
}
