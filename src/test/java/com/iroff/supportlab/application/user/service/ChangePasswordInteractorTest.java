package com.iroff.supportlab.application.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.iroff.supportlab.application.user.dto.ChangePasswordRequest;
import com.iroff.supportlab.domain.auth.model.vo.VerificationType;
import com.iroff.supportlab.domain.auth.port.in.exception.AuthError;
import com.iroff.supportlab.domain.auth.port.out.VerificationCodeRepository;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.user.model.User;
import com.iroff.supportlab.domain.user.port.in.exception.UserError;
import com.iroff.supportlab.domain.user.port.out.UserRepository;

@ExtendWith(MockitoExtension.class)
class ChangePasswordInteractorTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private VerificationCodeRepository verificationCodeRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private ChangePasswordInteractor changePasswordInteractor;

	private ChangePasswordRequest changePasswordRequest;

	@BeforeEach
	void setUp() {
		changePasswordRequest = new ChangePasswordRequest("valid-token", "newPassword12!");
	}

	@Test
	@DisplayName("비밀번호 변경 성공")
	void changePassword_success() {
		User user = spy(User.builder().id(1L).build());
		when(verificationCodeRepository.find(any(VerificationType.class), anyString())).thenReturn(Optional.of("1"));
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

		changePasswordInteractor.changePassword(changePasswordRequest);

		verify(verificationCodeRepository).remove(VerificationType.RESET_PASSWORD, "valid-token");
		verify(user).changePassword("encodedPassword");
	}

	@Test
	@DisplayName("비밀번호 변경 실패 - 만료된 토큰")
	void changePassword_fail_expiredToken() {
		when(verificationCodeRepository.find(any(VerificationType.class), anyString())).thenReturn(Optional.empty());

		DomainException exception = assertThrows(DomainException.class, () -> {
			changePasswordInteractor.changePassword(changePasswordRequest);
		});

		assertEquals(AuthError.EXPIRED_TOKEN, exception.getError());
	}

	@Test
	@DisplayName("비밀번호 변경 실패 - 사용자를 찾을 수 없음")
	void changePassword_fail_userNotFound() {
		when(verificationCodeRepository.find(any(VerificationType.class), anyString())).thenReturn(Optional.of("1"));
		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

		DomainException exception = assertThrows(DomainException.class, () -> {
			changePasswordInteractor.changePassword(changePasswordRequest);
		});

		assertEquals(UserError.USER_NOT_FOUND, exception.getError());
	}
} 