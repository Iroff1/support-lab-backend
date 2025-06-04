package com.iroff.supportlab.adapter.auth.in.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iroff.supportlab.adapter.common.in.web.exception.APIException;
import com.iroff.supportlab.adapter.common.in.web.exception.ErrorStatus;
import com.iroff.supportlab.adapter.common.in.web.exception.ErrorStatusResolver;
import com.iroff.supportlab.application.auth.dto.LoginRequest;
import com.iroff.supportlab.application.auth.dto.LoginResponse;
import com.iroff.supportlab.application.auth.dto.SendCodeRequest;
import com.iroff.supportlab.application.auth.dto.VerifyCodeRequest;
import com.iroff.supportlab.application.auth.dto.VerifyCodeResponse;
import com.iroff.supportlab.domain.auth.port.in.LoginUseCase;
import com.iroff.supportlab.domain.auth.port.in.SendCodeUseCase;
import com.iroff.supportlab.domain.auth.port.in.VerifyCodeUseCase;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "인증", description = "인증 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
	private final SendCodeUseCase sendCodeUseCase;
	private final VerifyCodeUseCase verifyCodeUseCase;
	private final LoginUseCase loginUseCase;
	private final ErrorStatusResolver errorStatusResolver;

	@Operation(summary = "인증 코드 발송", description = "휴대폰 번호로 인증 코드를 발송합니다. 회원가입, 이메일 찾기, 비밀번호 찾기에 사용됩니다.")
	@PostMapping("/send-code")
	public ResponseEntity<Void> sendCode(
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
			return ResponseEntity.ok().build();
		} catch (DomainException e) {
			ErrorStatus errorStatus = errorStatusResolver.resolve(e.getError());
			throw new APIException(e, errorStatus);
		}
	}

	@Operation(summary = "인증 코드 검증", description = "발송된 인증 코드를 검증합니다.")
	@PostMapping("/verify-code")
	public ResponseEntity<VerifyCodeResponse> verifyCode(
		@Valid @RequestBody VerifyCodeRequest request
	) {
		try {
			VerifyCodeResponse response = verifyCodeUseCase.verifyCode(request);
			return ResponseEntity.ok().body(response);
		} catch (DomainException e) {
			ErrorStatus errorStatus = errorStatusResolver.resolve(e.getError());
			throw new APIException(e, errorStatus);
		}
	}

	@Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인합니다. 성공 시 JWT 토큰이 발급됩니다.")
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
		try {
			LoginResponse response = loginUseCase.login(request);
			return ResponseEntity.ok()
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + response.accessToken())
				.body(response);
		} catch (DomainException e) {
			ErrorStatus errorStatus = errorStatusResolver.resolve(e.getError());
			throw new APIException(e, errorStatus);
		}
	}
}
