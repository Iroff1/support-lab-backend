package com.iroff.supportlab.adapter.auth.in.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iroff.supportlab.adapter.auth.in.web.exception.AuthErrorStatus;
import com.iroff.supportlab.adapter.common.in.web.exception.APIException;
import com.iroff.supportlab.application.auth.dto.LoginRequest;
import com.iroff.supportlab.application.auth.dto.LoginResponse;
import com.iroff.supportlab.application.auth.dto.SendCodeRequest;
import com.iroff.supportlab.application.auth.dto.VerifyCodeRequest;
import com.iroff.supportlab.application.auth.dto.VerifyCodeResponse;
import com.iroff.supportlab.application.common.dto.ResponseDTO;
import com.iroff.supportlab.application.common.dto.vo.ResponseCode;
import com.iroff.supportlab.domain.auth.port.in.LoginUseCase;
import com.iroff.supportlab.domain.auth.port.in.SendCodeUseCase;
import com.iroff.supportlab.domain.auth.port.in.VerifyCodeUseCase;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
	private final SendCodeUseCase sendCodeUseCase;
	private final VerifyCodeUseCase verifyCodeUseCase;
	private final AuthErrorStatus authErrorStatus;
	private final LoginUseCase loginUseCase;

	@PostMapping("/send-code")
	public ResponseEntity<ResponseDTO<Void>> sendCode(
		@Valid @RequestBody SendCodeRequest request,
		HttpServletRequest servletRequest
	) {
		String xff = servletRequest.getHeader("X-Forwarded-For");
		String ip;
		if (xff != null && !xff.isBlank()) {
			ip = xff.split(",")[0].trim();
		} else {
			ip = servletRequest.getRemoteAddr();
		}
		try {
			sendCodeUseCase.sendCode(request, ip);
			ResponseDTO<Void> response = new ResponseDTO<>(ResponseCode.OK, null);
			return ResponseEntity.ok().body(response);
		} catch (DomainException ex) {
			throw new APIException(ex, authErrorStatus);
		}
	}

	@PostMapping("/verify-code")
	public ResponseEntity<ResponseDTO<VerifyCodeResponse>> verifyCode(
		@Valid @RequestBody VerifyCodeRequest request
	) {
		try {
			VerifyCodeResponse response = verifyCodeUseCase.verifyCode(request);
			ResponseDTO<VerifyCodeResponse> dto = new ResponseDTO<>(ResponseCode.OK, response);
			return ResponseEntity.ok().body(dto);
		} catch (DomainException ex) {
			throw new APIException(ex, authErrorStatus);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<ResponseDTO<LoginResponse>> login(@RequestBody LoginRequest request) {
		LoginResponse response = loginUseCase.login(request);
		ResponseDTO<LoginResponse> responseDTO = new ResponseDTO<>(ResponseCode.OK, response);
		return ResponseEntity.ok()
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + response.accessToken())
			.body(responseDTO);
	}

}
