package com.iroff.supportlab.application.common.dto;

import com.iroff.supportlab.application.common.dto.vo.ResponseCode;

public record ResponseDTO<T>(
	String code,
	String status,
	String message,
	T data
) {
	public ResponseDTO(ResponseCode code, T data) {
		this(code.getCode(), code.getStatus(), code.getMessage(), data);
	}
}
