package com.iroff.supportlab.adapter.common.in.web.dto;

import com.iroff.supportlab.adapter.common.in.web.dto.vo.ResponseCode;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ResponseDTO", description = "공통 응답 DTO")
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
