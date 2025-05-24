package com.iroff.supportlab.adapter.common.in.web;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.iroff.supportlab.adapter.common.in.web.dto.ResponseDTO;
import com.iroff.supportlab.adapter.common.in.web.dto.vo.ResponseCode;

@RestControllerAdvice(basePackages = "com.iroff.supportlab.adapter")
public class GlobalResponseWrapper implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter returnType,
		Class<? extends HttpMessageConverter<?>> converterType) {
		// 이미 ResponseDTO 이거나 에러 응답인 경우엔 래핑하지 않음
		return !ResponseDTO.class.isAssignableFrom(returnType.getParameterType());
	}

	@Override
	public Object beforeBodyWrite(Object body,
		MethodParameter returnType,
		MediaType selectedContentType,
		Class<? extends HttpMessageConverter<?>> selectedConverterType,
		ServerHttpRequest request,
		ServerHttpResponse response) {

		if (body instanceof ResponseDTO) {
			return body;
		}
		int rawStatus;
		if (response instanceof ServletServerHttpResponse servletResp) {
			rawStatus = servletResp.getServletResponse().getStatus();
		} else {
			rawStatus = HttpStatus.OK.value();
		}

		HttpStatus status = HttpStatus.resolve(rawStatus);
		ResponseCode code = ResponseCode.getResponseCode(status);

		return new ResponseDTO<>(code, body);
	}
}