package com.iroff.supportlab.adapter.common.in.web.dto;

import com.iroff.supportlab.adapter.common.in.web.dto.vo.ResponseCode;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ResponseDTO", description = "공통 응답 DTO")
public record ResponseDTO<T>(
	String code,
	String status,
	String message,
	T body
) {
	public ResponseDTO(ResponseCode code, T body) {
		this(code.getCode(), code.getStatus(), code.getMessage(), body);
	}
}
