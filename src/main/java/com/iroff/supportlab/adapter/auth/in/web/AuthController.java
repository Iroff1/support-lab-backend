package com.iroff.supportlab.adapter.auth.in.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iroff.supportlab.application.auth.dto.LoginRequest;
import com.iroff.supportlab.application.auth.dto.LoginResponse;
import com.iroff.supportlab.application.common.dto.ResponseDTO;
import com.iroff.supportlab.application.common.dto.vo.ResponseCode;
import com.iroff.supportlab.domain.auth.port.in.AuthUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthUseCase authUseCase;

	@PostMapping("/login")
	public ResponseEntity<ResponseDTO<LoginResponse>> login(@RequestBody LoginRequest request) {
		LoginResponse response = authUseCase.login(request);
		ResponseDTO<LoginResponse> responseDTO = new ResponseDTO<>(ResponseCode.OK, response);
		return ResponseEntity.ok()
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + response.accessToken())
			.body(responseDTO);
	}
}
