package com.iroff.supportlab.presentation.user.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iroff.supportlab.application.user.dto.SignUpUserRequest;
import com.iroff.supportlab.application.user.dto.SignUpUserResponse;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.user.port.in.SignUpUserUseCase;
import com.iroff.supportlab.presentation.common.in.web.exception.APIException;
import com.iroff.supportlab.presentation.user.in.web.exception.UserErrorStatus;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

	private final SignUpUserUseCase signUpUserUseCase;
	private final UserErrorStatus errorStatus;

	@PostMapping("/sign-up")
	public ResponseEntity<SignUpUserResponse> signUp(
		@RequestBody SignUpUserRequest request
	) {
		SignUpUserResponse response;
		try {
			response = signUpUserUseCase.signUp(request);
		} catch (DomainException e) {
			throw new APIException(e, errorStatus);
		}
		return ResponseEntity.ok(response);
	}
}
