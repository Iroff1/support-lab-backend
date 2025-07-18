package com.iroff.supportlab.adapter.common.in.web;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.iroff.supportlab.adapter.common.in.web.dto.ResponseDTO;
import com.iroff.supportlab.adapter.common.in.web.dto.vo.ResponseCode;

import reactor.core.publisher.Mono;

@RestControllerAdvice(basePackages = "com.iroff.supportlab.adapter")
public class GlobalResponseWrapper implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter returnType,
		Class<? extends HttpMessageConverter<?>> converterType) {
		// 이미 ResponseDTO 이거나 에러 응답인 경우엔 래핑하지 않음
		// Mono 타입도 래핑 대상에서 제외하여 beforeBodyWrite에서 Mono 내부를 처리하도록 함
		return !ResponseDTO.class.isAssignableFrom(returnType.getParameterType()) &&
			!Mono.class.isAssignableFrom(returnType.getParameterType());
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

		// Mono<T> 타입을 처리
		if (body instanceof Mono<?> monoBody) {
			return monoBody.map(actualBody -> {
				int rawStatus;
				if (response instanceof ServletServerHttpResponse servletResp) {
					rawStatus = servletResp.getServletResponse().getStatus();
				} else {
					rawStatus = HttpStatus.OK.value();
				}

				HttpStatus status = HttpStatus.resolve(rawStatus);
				ResponseCode code = ResponseCode.getResponseCode(status);

				// Mono 내부의 실제 바디가 ResponseEntity인 경우, 그 안의 바디를 추출
				Object finalBody = actualBody;
				if (actualBody instanceof ResponseEntity<?> responseEntity) {
					finalBody = responseEntity.getBody();
					// ResponseEntity의 상태 코드를 사용하여 ResponseDTO의 상태를 설정할 수도 있지만,
					// 현재 로직은 response 객체의 상태를 따르므로 그대로 둡니다.
				}
				return new ResponseDTO<>(code, finalBody);
			});
		}

		// 기존 동기 응답 처리 (Mono가 아닌 경우)
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