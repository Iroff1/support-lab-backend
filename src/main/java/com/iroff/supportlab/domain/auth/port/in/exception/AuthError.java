package com.iroff.supportlab.domain.auth.port.in.exception;

import com.iroff.supportlab.domain.common.port.in.exception.ErrorInfo;

public enum AuthError implements ErrorInfo {
	SEND_CODE_FAILED("A001", "인증 코드 전송에 실패했습니다."),
	VERIFY_CODE_FAILED("A002", "인증 코드 검증에 실패했습니다."),
	CODE_NOT_EXISTS("A003", "인증 코드가 만료되었거나 발급되지 않았습니다. 다시 발급해주세요.");

	private final String code;
	private final String desc;

	AuthError(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	@Override
	public String getDesc() {
		return desc;
	}

	@Override
	public String getCode() {
		return code;
	}
}
