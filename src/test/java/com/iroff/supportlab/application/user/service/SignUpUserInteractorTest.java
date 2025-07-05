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
			"홍길동",
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

	@Test
	@DisplayName("회원가입 실패 - 이름이 한 글자")
	void signUp_fail_singleCharacterName() {
		// given
		SignUpUserRequest invalidRequest = new SignUpUserRequest(
			"test@example.com",
			"Password12!",
			"홍",  // 한 글자
			"01012345678",
			true,
			true,
			false
		);

		// when & then
		DomainException exception = assertThrows(DomainException.class, () -> {
			signUpUserInteractor.signUp(invalidRequest);
		});
		assertEquals(UserError.INVALID_NAME, exception.getError());

		verify(userRepository, never()).save(any());
	}

	@Test
	@DisplayName("회원가입 실패 - 이름이 너무 긴 경우")
	void signUp_fail_nameTooLong() {
		// given
		SignUpUserRequest invalidRequest = new SignUpUserRequest(
			"test@example.com",
			"Password12!",
			"홍길동일이삼사",  // 7글자
			"01012345678",
			true,
			true,
			false
		);

		// when & then
		DomainException exception = assertThrows(DomainException.class, () -> {
			signUpUserInteractor.signUp(invalidRequest);
		});
		assertEquals(UserError.INVALID_NAME, exception.getError());

		verify(userRepository, never()).save(any());
	}

	@Test
	@DisplayName("회원가입 실패 - 한글이 아닌 문자가 포함된 이름")
	void signUp_fail_nonKoreanCharactersInName() {
		// given
		SignUpUserRequest invalidRequest = new SignUpUserRequest(
			"test@example.com",
			"Password12!",
			"Hong",  // 영문
			"01012345678",
			true,
			true,
			false
		);

		// when & then
		DomainException exception = assertThrows(DomainException.class, () -> {
			signUpUserInteractor.signUp(invalidRequest);
		});
		assertEquals(UserError.INVALID_NAME, exception.getError());

		verify(userRepository, never()).save(any());
	}

	@Test
	@DisplayName("회원가입 실패 - 특수문자가 포함된 이름")
	void signUp_fail_specialCharactersInName() {
		// given
		SignUpUserRequest invalidRequest = new SignUpUserRequest(
			"test@example.com",
			"Password12!",
			"홍길★동",  // 특수문자 포함
			"01012345678",
			true,
			true,
			false
		);

		// when & then
		DomainException exception = assertThrows(DomainException.class, () -> {
			signUpUserInteractor.signUp(invalidRequest);
		});
		assertEquals(UserError.INVALID_NAME, exception.getError());

		verify(userRepository, never()).save(any());
	}

	@Test
	@DisplayName("회원가입 실패 - 공백이 포함된 이름")
	void signUp_fail_nameContainsWhitespace() {
		// given
		SignUpUserRequest invalidRequest = new SignUpUserRequest(
			"test@example.com",
			"Password12!",
			"홍 길동",  // 공백 포함
			"01012345678",
			true,
			true,
			false
		);

		// when & then
		DomainException exception = assertThrows(DomainException.class, () -> {
			signUpUserInteractor.signUp(invalidRequest);
		});
		assertEquals(UserError.INVALID_NAME, exception.getError());

		verify(userRepository, never()).save(any());
	}
} 