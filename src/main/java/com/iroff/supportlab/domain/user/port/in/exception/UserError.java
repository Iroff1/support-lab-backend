package com.iroff.supportlab.domain.user.port.in.exception;

import com.iroff.supportlab.domain.common.port.in.exception.ErrorInfo;

public enum UserError implements ErrorInfo {
	EMAIL_ALREADY_EXISTS("U001", "이미 존재하는 이메일입니다."),
	INVALID_EMAIL("U002", "유효하지 않은 이메일 형식입니다.");

	UserError(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	private final String code;
	private final String desc;

	@Override
	public String getCode() {
		return code;
	}
	
	@Override
	public String getDesc() {
		return desc;
	}
}
