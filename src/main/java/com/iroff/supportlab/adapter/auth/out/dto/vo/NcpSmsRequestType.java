package com.iroff.supportlab.adapter.auth.out.dto.vo;

import lombok.Getter;

@Getter
public enum NcpSmsRequestType {
	SMS("SMS"),
	LMS("LMS"),
	MMS("MMS");

	private final String value;

	NcpSmsRequestType(String value) {
		this.value = value;
	}

}
