package com.iroff.supportlab.adapter.user.in.web;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iroff.supportlab.adapter.common.in.web.exception.APIException;
import com.iroff.supportlab.adapter.common.in.web.exception.ErrorStatus;
import com.iroff.supportlab.adapter.common.in.web.exception.ErrorStatusResolver;
import com.iroff.supportlab.application.user.dto.ChangePasswordRequest;
import com.iroff.supportlab.application.user.dto.FindEmailRequest;
import com.iroff.supportlab.application.user.dto.FindEmailResponse;
import com.iroff.supportlab.application.user.dto.RequestChangePasswordRequest;
import com.iroff.supportlab.application.user.dto.RequestChangePasswordResponse;
import com.iroff.supportlab.application.user.dto.SignUpUserRequest;
import com.iroff.supportlab.application.user.dto.SignUpUserResponse;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.user.port.in.ChangePasswordUseCase;
import com.iroff.supportlab.domain.user.port.in.FindEmailUseCase;
import com.iroff.supportlab.domain.user.port.in.RequestChangePasswordUseCase;
import com.iroff.supportlab.domain.user.port.in.SignUpUserUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private static final String USER_PATH = "/api/users";
	private final SignUpUserUseCase signUpUserUseCase;
	private final FindEmailUseCase findEmailUseCase;
	private final RequestChangePasswordUseCase requestChangePasswordUseCase;
	private final ErrorStatusResolver errorStatusResolver;
	private final ChangePasswordUseCase changePasswordUseCase;

	@PostMapping("/sign-up")
	public ResponseEntity<SignUpUserResponse> signUp(
		@Valid @RequestBody SignUpUserRequest request
	) {
		try {
			SignUpUserResponse response = signUpUserUseCase.signUp(request);
			URI location = URI.create(USER_PATH + "/" + response.id());
			return ResponseEntity.created(location).body(response);
		} catch (DomainException e) {
			ErrorStatus errorStatus = errorStatusResolver.resolve(e.getError());
			throw new APIException(e, errorStatus);
		}
	}

	@GetMapping("/email")
	public ResponseEntity<FindEmailResponse> findEmail(
		@Valid FindEmailRequest request
	) {
		try {
			FindEmailResponse response = findEmailUseCase.findEmail(request);
			return ResponseEntity.ok().body(response);
		} catch (DomainException e) {
			ErrorStatus errorStatus = errorStatusResolver.resolve(e.getError());
			throw new APIException(e, errorStatus);
		}
	}

	@PostMapping("/password")
	public ResponseEntity<RequestChangePasswordResponse> requestChangePassword(
		@Valid @RequestBody RequestChangePasswordRequest request
	) {
		try {
			RequestChangePasswordResponse response = requestChangePasswordUseCase.requestChangePassword(request);
			return ResponseEntity.ok().body(response);
		} catch (DomainException e) {
			ErrorStatus errorStatus = errorStatusResolver.resolve(e.getError());
			throw new APIException(e, errorStatus);
		}
	}

	@PatchMapping("/password")
	public ResponseEntity<Void> changePassword(
		@Valid @RequestBody ChangePasswordRequest request
	) {
		try {
			changePasswordUseCase.changePassword(request);
			return ResponseEntity.ok().build();
		} catch (DomainException e) {
			ErrorStatus errorStatus = errorStatusResolver.resolve(e.getError());
			throw new APIException(e, errorStatus);
		}
	}
}
