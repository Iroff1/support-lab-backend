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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "사용자", description = "사용자 관련 API")
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

	@Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다. 휴대폰 인증이 완료된 상태여야 합니다.")
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

	@Operation(summary = "이메일 찾기", description = "이름과 휴대폰 번호로 이메일을 찾습니다. 휴대폰 인증이 완료된 상태여야 합니다.")
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

	@Operation(summary = "비밀번호 변경 요청", description = "비밀번호 변경을 위한 토큰을 발급받습니다. 휴대폰 인증이 완료된 상태여야 합니다.")
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

	@Operation(summary = "비밀번호 변경", description = "발급받은 토큰으로 비밀번호를 변경합니다.")
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
