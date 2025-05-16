package com.iroff.supportlab.adapter.auth.out.dto.vo;

import com.fasterxml.jackson.annotation.JsonValue;

public enum NcpSmsRequestCountryCode {
	KOREA("82");

	private final String value;

	NcpSmsRequestCountryCode(String value) {
		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return value;
	}
}
