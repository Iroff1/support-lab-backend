package com.iroff.supportlab.application.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.iroff.supportlab.application.user.dto.SignUpUserRequest;
import com.iroff.supportlab.application.user.dto.SignUpUserResponse;
import com.iroff.supportlab.domain.auth.model.vo.VerificationType;
import com.iroff.supportlab.domain.auth.port.out.VerificationStateRepository;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.user.port.in.exception.UserError;
import com.iroff.supportlab.domain.user.port.out.UserRepository;

@ExtendWith(MockitoExtension.class)
class SignUpUserInteractorTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private VerificationStateRepository verificationStateRepository;

	@InjectMocks
	private SignUpUserInteractor signUpUserInteractor;

	private SignUpUserRequest signUpUserRequest;

	@BeforeEach
	void setUp() {
		signUpUserRequest = new SignUpUserRequest(
			"test@example.com",
			"Password12!",
			"Test User",
			"01012345678",
			true,
			true,
			false
		);
	}

	@Test
	@DisplayName("회원가입 성공")
	void signUp_success() {
		when(userRepository.existsByEmail(anyString())).thenReturn(false);
		when(userRepository.existsByPhone(anyString())).thenReturn(false);
		when(verificationStateRepository.isVerified(any(VerificationType.class), anyString())).thenReturn(true);
		when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

		SignUpUserResponse response = signUpUserInteractor.signUp(signUpUserRequest);

		assertNotNull(response);
		assertEquals(signUpUserRequest.email(), response.email());
	}

	@Test
	@DisplayName("회원가입 실패 - 이메일 중복")
	void signUp_fail_emailAlreadyExists() {
		when(userRepository.existsByEmail(anyString())).thenReturn(true);

		DomainException exception = assertThrows(DomainException.class, () -> {
			signUpUserInteractor.signUp(signUpUserRequest);
		});
		assertEquals(UserError.EMAIL_ALREADY_EXISTS, exception.getError());
	}

	@Test
	@DisplayName("회원가입 실패 - 휴대폰 번호 중복")
	void signUp_fail_phoneAlreadyExists() {
		when(userRepository.existsByEmail(anyString())).thenReturn(false);
		when(userRepository.existsByPhone(anyString())).thenReturn(true);

		DomainException exception = assertThrows(DomainException.class, () -> {
			signUpUserInteractor.signUp(signUpUserRequest);
		});
		assertEquals(UserError.PHONE_ALREADY_EXISTS, exception.getError());
	}

	@Test
	@DisplayName("회원가입 실패 - 인증 실패")
	void signUp_fail_verificationFailed() {
		when(userRepository.existsByEmail(anyString())).thenReturn(false);
		when(userRepository.existsByPhone(anyString())).thenReturn(false);
		when(verificationStateRepository.isVerified(any(VerificationType.class), anyString())).thenReturn(false);

		DomainException exception = assertThrows(DomainException.class, () -> {
			signUpUserInteractor.signUp(signUpUserRequest);
		});
		assertEquals(UserError.VERIFICATION_FAILED, exception.getError());
	}
} 