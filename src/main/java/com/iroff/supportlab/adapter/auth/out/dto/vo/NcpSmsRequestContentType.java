package com.iroff.supportlab.adapter.auth.out.dto.vo;

import lombok.Getter;

@Getter
public enum NcpSmsRequestContentType {
	COMM("COMM"),
	AD("AD");

	private final String value;

	NcpSmsRequestContentType(String value) {
		this.value = value;
	}
}
