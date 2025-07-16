package com.iroff.supportlab.adapter.user.in.web;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iroff.supportlab.adapter.common.in.web.exception.APIException;
import com.iroff.supportlab.adapter.common.in.web.exception.ErrorStatus;
import com.iroff.supportlab.adapter.common.in.web.exception.ErrorStatusResolver;
import com.iroff.supportlab.application.user.dto.ChangePasswordRequest;
import com.iroff.supportlab.application.user.dto.CheckEmailExistsRequest;
import com.iroff.supportlab.application.user.dto.CheckEmailExistsResponse;
import com.iroff.supportlab.application.user.dto.DeleteUserRequest;
import com.iroff.supportlab.application.user.dto.FindEmailRequest;
import com.iroff.supportlab.application.user.dto.FindEmailResponse;
import com.iroff.supportlab.application.user.dto.GetUserInfoResponse;
import com.iroff.supportlab.application.user.dto.RequestChangePasswordRequest;
import com.iroff.supportlab.application.user.dto.RequestChangePasswordResponse;
import com.iroff.supportlab.application.user.dto.SignUpUserRequest;
import com.iroff.supportlab.application.user.dto.SignUpUserResponse;
import com.iroff.supportlab.application.user.dto.UpdateMarketingAgreedRequest;
import com.iroff.supportlab.application.user.dto.UpdateNameRequest;
import com.iroff.supportlab.application.user.dto.UpdatePasswordRequest;
import com.iroff.supportlab.application.user.dto.UpdatePhoneNumberRequest;
import com.iroff.supportlab.application.user.dto.VerifyPasswordRequest;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.user.port.in.ChangePasswordUseCase;
import com.iroff.supportlab.domain.user.port.in.CheckEmailExistsUseCase;
import com.iroff.supportlab.domain.user.port.in.DeleteUserUseCase;
import com.iroff.supportlab.domain.user.port.in.FindEmailUseCase;
import com.iroff.supportlab.domain.user.port.in.GetUserInfoUseCase;
import com.iroff.supportlab.domain.user.port.in.RequestChangePasswordUseCase;
import com.iroff.supportlab.domain.user.port.in.SignUpUserUseCase;
import com.iroff.supportlab.domain.user.port.in.UpdateMarketingAgreedUseCase;
import com.iroff.supportlab.domain.user.port.in.UpdateNameUseCase;
import com.iroff.supportlab.domain.user.port.in.UpdatePasswordUseCase;
import com.iroff.supportlab.domain.user.port.in.UpdatePhoneNumberUseCase;
import com.iroff.supportlab.domain.user.port.in.VerifyPasswordUseCase;
import com.iroff.supportlab.framework.config.security.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
	private final GetUserInfoUseCase getUserInfoUseCase;
	private final DeleteUserUseCase deleteUserUseCase;
	private final CheckEmailExistsUseCase checkEmailExistsUseCase;
	private final UpdatePasswordUseCase updatePasswordUseCase;
	private final UpdatePhoneNumberUseCase updatePhoneNumberUseCase;
	private final UpdateNameUseCase updateNameUseCase;
	private final UpdateMarketingAgreedUseCase updateMarketingAgreedUseCase;
	private final VerifyPasswordUseCase verifyPasswordUseCase;

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
		@Valid @ModelAttribute FindEmailRequest request
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

	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "사용자 정보 조회", description = "액세스 토큰을 통해 사용자 정보를 조회합니다.")
	@GetMapping("/me")
	public ResponseEntity<GetUserInfoResponse> getUserInfo(
		@AuthenticationPrincipal CustomUserDetails user
	) {
		try {
			Long userId = user.getUser().getId();
			GetUserInfoResponse response = getUserInfoUseCase.getUserInfo(userId);
			return ResponseEntity.ok().body(response);
		} catch (DomainException e) {
			ErrorStatus errorStatus = errorStatusResolver.resolve(e.getError());
			throw new APIException(e, errorStatus);
		}
	}

	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "회원탈퇴", description = "액세스 토큰을 통해 회원탈퇴를 진행합니다.")
	@DeleteMapping("/me")
	public ResponseEntity<Void> deleteUser(
		@AuthenticationPrincipal CustomUserDetails user,
		@Valid @RequestBody DeleteUserRequest request
	) {
		try {
			Long userId = user.getUser().getId();
			deleteUserUseCase.deleteUser(userId, request);
			return ResponseEntity.ok().build();
		} catch (DomainException e) {
			ErrorStatus errorStatus = errorStatusResolver.resolve(e.getError());
			throw new APIException(e, errorStatus);
		}
	}

	@Operation(summary = "이메일 중복 체크", description = "이미 가입된 이메일인지 확인합니다.")
	@GetMapping("/existence")
	public ResponseEntity<CheckEmailExistsResponse> checkEmailExists(
		@Valid @ModelAttribute CheckEmailExistsRequest request
	) {
		try {
			String email = request.email();
			CheckEmailExistsResponse response = checkEmailExistsUseCase.checkEmailExists(email);
			return ResponseEntity.ok().body(response);
		} catch (DomainException e) {
			ErrorStatus errorStatus = errorStatusResolver.resolve(e.getError());
			throw new APIException(e, errorStatus);
		}
	}

	@SecurityRequirement(name = "bearerAuth")
	@Operation(description = "비밀번호 수정", summary = "기존 비밀번호에서 새로운 비밀번호로 수정합니다.")
	@PatchMapping("/me/password")
	public ResponseEntity<Void> updatePassword(
		@AuthenticationPrincipal CustomUserDetails user,
		@Valid @RequestBody UpdatePasswordRequest request
	) {
		try {
			Long userId = user.getUser().getId();
			updatePasswordUseCase.updatePassword(userId, request);
			return ResponseEntity.ok().build();
		} catch (DomainException e) {
			ErrorStatus errorStatus = errorStatusResolver.resolve(e.getError());
			throw new APIException(e, errorStatus);
		}
	}

	@SecurityRequirement(name = "bearerAuth")
	@Operation(description = "전화번호 수정", summary = "회원의 전화번호를 수정합니다.")
	@PatchMapping("/me/phone")
	public ResponseEntity<Void> updatePhoneNumber(
		@AuthenticationPrincipal CustomUserDetails user,
		@Valid @RequestBody UpdatePhoneNumberRequest request
	) {
		try {
			Long userId = user.getUser().getId();
			updatePhoneNumberUseCase.updatePhoneNumber(userId, request);
			return ResponseEntity.ok().build();
		} catch (DomainException e) {
			ErrorStatus errorStatus = errorStatusResolver.resolve(e.getError());
			throw new APIException(e, errorStatus);
		}
	}

	@SecurityRequirement(name = "bearerAuth")
	@Operation(description = "사용자 이름 수정", summary = "사용자의 이름을 수정합니다.")
	@PatchMapping("/me/name")
	public ResponseEntity<Void> updateName(
		@AuthenticationPrincipal CustomUserDetails user,
		@Valid @RequestBody UpdateNameRequest request
	) {
		try {
			Long userId = user.getUser().getId();
			updateNameUseCase.updateName(userId, request);
			return ResponseEntity.ok().build();
		} catch (DomainException e) {
			ErrorStatus errorStatus = errorStatusResolver.resolve(e.getError());
			throw new APIException(e, errorStatus);
		}
	}

	@SecurityRequirement(name = "bearerAuth")
	@Operation(description = "마케팅 동의 여부 수정", summary = "마케팅 동의 여부를 수정합니다.")
	@PatchMapping("/me/marketing-agreed")
	public ResponseEntity<Void> updateMarketingAgreed(
		@AuthenticationPrincipal CustomUserDetails user,
		@Valid @RequestBody UpdateMarketingAgreedRequest request
	) {
		try {
			Long userId = user.getUser().getId();
			updateMarketingAgreedUseCase.updateMarketingAgreed(userId, request);
			return ResponseEntity.ok().build();
		} catch (DomainException e) {
			ErrorStatus errorStatus = errorStatusResolver.resolve(e.getError());
			throw new APIException(e, errorStatus);
		}
	}

	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "비밀번호 검증", description = "사용자 정보 수정을 위해 현재 비밀번호를 검증합니다. 성공 시 10분간 정보 수정이 가능한 상태가 됩니다.")
	@PostMapping("/me/verify-password")
	public ResponseEntity<Void> verifyPassword(
		@AuthenticationPrincipal CustomUserDetails user,
		@Valid @RequestBody VerifyPasswordRequest request
	) {
		try {
			Long userId = user.getUser().getId();
			verifyPasswordUseCase.verifyPassword(userId, request.password());
			return ResponseEntity.ok().build();
		} catch (DomainException e) {
			ErrorStatus errorStatus = errorStatusResolver.resolve(e.getError());
			throw new APIException(e, errorStatus);
		}
	}
}
