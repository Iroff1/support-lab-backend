package com.iroff.supportlab.domain.email.port.in.exception;

import com.iroff.supportlab.domain.common.port.in.exception.ErrorInfo;

import lombok.Getter;

@Getter
public enum EmailError implements ErrorInfo {
	SEND_EMAIL_FAILED("이메일 전송에 실패했습니다.");

	private final String desc;

	EmailError(String desc) {
		this.desc = desc;
	}

	@Override
	public String getCode() {
		return String.format("E%03d", ordinal() + 1);
	}

	@Override
	public String getDesc() {
		return desc;
	}
}
