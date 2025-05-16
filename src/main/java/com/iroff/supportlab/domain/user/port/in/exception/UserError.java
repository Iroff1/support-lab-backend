package com.iroff.supportlab.domain.user.port.in.exception;

import com.iroff.supportlab.domain.common.port.in.exception.ErrorInfo;

public enum UserError implements ErrorInfo {
	EMAIL_ALREADY_EXISTS("U001", "이미 존재하는 이메일입니다."),
	INVALID_EMAIL("U002", "유효하지 않은 이메일 형식입니다."),
	INVALID_PASSWORD_LENGTH("U003", "비밀번호는 8자 이상이어야 합니다."),
	INVALID_PASSWORD_UPPERCASE("U004", "비밀번호는 최소 하나 이상의 대문자를 포함해야 합니다."),
	INVALID_PASSWORD_LOWERCASE("U005", "비밀번호는 최소 하나 이상의 소문자를 포함해야 합니다."),
	INVALID_PASSWORD_NUMBER("U006", "비밀번호는 최소 하나 이상의 숫자를 포함해야 합니다."),
	INVALID_PASSWORD_SPECIAL("U007", "비밀번호는 최소 하나 이상의 특수문자를 포함해야 합니다."),
	INVALID_PASSWORD_SEQUENCE("U008", "연속된 문자나 반복된 문자는 사용할 수 없습니다.");

	private final String code;
	private final String desc;
	
	UserError(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getDesc() {
		return desc;
	}
}
