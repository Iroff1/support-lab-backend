package com.iroff.supportlab.framework.config.security;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iroff.supportlab.adapter.common.in.web.dto.ResponseDTO;
import com.iroff.supportlab.adapter.common.in.web.dto.vo.ResponseCode;
import com.iroff.supportlab.adapter.common.in.web.exception.ErrorResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;

	@Override
	public void commence(
		HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException authException
	) throws IOException, ServletException {

		log.warn("Unauthorized error: {} for request URI: {} {}",
			authException.getMessage(), request.getMethod(), request.getRequestURI());

		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		ResponseCode responseCode = ResponseCode.UNAUTHORIZED;
		String customMessage = "인증이 필요합니다. 유효한 토큰을 제공해주세요.";
		ErrorResponse errorResponse = new ErrorResponse("E001", "UNAUTHORIZED", customMessage, LocalDateTime.now());

		ResponseDTO<ErrorResponse> responseDTO = new ResponseDTO<>(responseCode, errorResponse);

		response.getWriter().write(objectMapper.writeValueAsString(responseDTO));
	}
}
