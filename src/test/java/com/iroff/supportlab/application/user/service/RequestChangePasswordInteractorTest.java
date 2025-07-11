package com.iroff.supportlab.application.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.Duration;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iroff.supportlab.application.user.dto.RequestChangePasswordRequest;
import com.iroff.supportlab.application.user.dto.RequestChangePasswordResponse;
import com.iroff.supportlab.domain.auth.model.vo.VerificationType;
import com.iroff.supportlab.domain.auth.port.in.exception.AuthError;
import com.iroff.supportlab.domain.auth.port.out.VerificationCodeRepository;
import com.iroff.supportlab.domain.auth.port.out.VerificationStateRepository;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.user.model.User;
import com.iroff.supportlab.domain.user.port.out.UserRepository;

@ExtendWith(MockitoExtension.class)
class RequestChangePasswordInteractorTest {

	@Mock
	private UserRepository userRepository;
	@Mock
	private VerificationCodeRepository verificationCodeRepository;
	@Mock
	private VerificationStateRepository verificationStateRepository;

	@InjectMocks
	private RequestChangePasswordInteractor requestChangePasswordInteractor;

	private RequestChangePasswordRequest request;
	private User user;

	@BeforeEach
	void setUp() {
		request = new RequestChangePasswordRequest("test@example.com", "Test User", "01012345678");
		user = User.builder()
			.id(1L)
			.email("test@example.com")
			.name("Test User")
			.phone("01012345678")
			.build();
	}

	@Test
	@DisplayName("비밀번호 변경 요청 성공")
	void requestChangePassword_success() {
		when(verificationStateRepository.isVerified(any(VerificationType.class), anyString())).thenReturn(true);
		when(userRepository.findByPhone(anyString())).thenReturn(Optional.of(user));

		RequestChangePasswordResponse response = requestChangePasswordInteractor.requestChangePassword(request);

		assertNotNull(response.token());
		verify(verificationCodeRepository).save(any(VerificationType.class), anyString(), anyString(),
			any(Duration.class));
	}

	@Test
	@DisplayName("비밀번호 변경 요청 실패 - 인증 실패")
	void requestChangePassword_fail_verificationFailed() {
		when(verificationStateRepository.isVerified(any(VerificationType.class), anyString())).thenReturn(false);

		DomainException exception = assertThrows(DomainException.class, () -> {
			requestChangePasswordInteractor.requestChangePassword(request);
		});
		assertEquals(AuthError.VERIFY_CODE_FAILED, exception.getError());
	}

	@Test
	@DisplayName("비밀번호 변경 요청 실패 - 잘못된 사용자 정보")
	void requestChangePassword_fail_invalidAuthorization() {
		when(verificationStateRepository.isVerified(any(VerificationType.class), anyString())).thenReturn(true);
		when(userRepository.findByPhone(anyString())).thenReturn(
			Optional.of(User.builder().name("Wrong User").build()));

		DomainException exception = assertThrows(DomainException.class, () -> {
			requestChangePasswordInteractor.requestChangePassword(request);
		});
		assertEquals(AuthError.INVALID_AUTHORIZATION, exception.getError());
	}
} 