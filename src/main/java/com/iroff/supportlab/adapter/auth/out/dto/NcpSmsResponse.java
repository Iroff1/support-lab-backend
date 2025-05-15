package com.iroff.supportlab.adapter.auth.out.dto;

public record NcpSmsResponse(
	String requestId,
	String requestTime,
	String statusCode,
	String statusName
) {
}
