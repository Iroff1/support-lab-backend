package com.iroff.supportlab.application.common.dto;

import com.iroff.supportlab.application.common.dto.vo.ResponseCode;

public record ResponseDTO<T>(
	ResponseCode code,
	T data
) {
}
