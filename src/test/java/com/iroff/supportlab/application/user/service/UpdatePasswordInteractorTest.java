package com.iroff.supportlab.application.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.iroff.supportlab.application.user.dto.UpdatePasswordRequest;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.user.model.User;
import com.iroff.supportlab.domain.user.port.in.exception.UserError;
import com.iroff.supportlab.domain.user.port.out.UserRepository;

@ExtendWith(MockitoExtension.class)
class UpdatePasswordInteractorTest {

	private final Long USER_ID = 1L;
	private final String OLD_PASSWORD = "oldPassword12!";
	private final String NEW_PASSWORD = "newPassword12!";
	private final String ENCODED_PASSWORD = "encodedPassword12!";
	@Mock
	private UserRepository userRepository;
	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private User mockUser;
	@InjectMocks
	private UpdatePasswordInteractor updatePasswordInteractor;

	@Test
	@DisplayName("비밀번호 업데이트 성공 테스트")
	void updatePassword_Success() {
		// given
		UpdatePasswordRequest request = new UpdatePasswordRequest(OLD_PASSWORD, NEW_PASSWORD);
		when(userRepository.findById(USER_ID)).thenReturn(Optional.of(mockUser));
		when(mockUser.getPassword()).thenReturn(ENCODED_PASSWORD);
		when(passwordEncoder.matches(OLD_PASSWORD, ENCODED_PASSWORD)).thenReturn(true);
		when(passwordEncoder.encode(NEW_PASSWORD)).thenReturn(ENCODED_PASSWORD);

		// when
		assertDoesNotThrow(() -> updatePasswordInteractor.updatePassword(USER_ID, request));

		// then
		verify(userRepository).findById(USER_ID);
		verify(passwordEncoder).matches(OLD_PASSWORD, ENCODED_PASSWORD);
		verify(mockUser).changePassword(ENCODED_PASSWORD);
	}

	@Test
	@DisplayName("존재하지 않는 사용자 테스트")
	void updatePassword_UserNotFound() {
		// given
		UpdatePasswordRequest request = new UpdatePasswordRequest(OLD_PASSWORD, NEW_PASSWORD);
		when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

		// when & then
		DomainException exception = assertThrows(DomainException.class,
			() -> updatePasswordInteractor.updatePassword(USER_ID, request));
		assertEquals(UserError.USER_NOT_FOUND, exception.getError());
		verify(userRepository).findById(USER_ID);
		verifyNoInteractions(passwordEncoder);
	}

	@Test
	@DisplayName("이전 비밀번호와 새 비밀번호가 동일한 경우 테스트")
	void updatePassword_SamePassword() {
		// given
		UpdatePasswordRequest request = new UpdatePasswordRequest(OLD_PASSWORD, OLD_PASSWORD);
		when(userRepository.findById(USER_ID)).thenReturn(Optional.of(mockUser));

		// when & then
		DomainException exception = assertThrows(DomainException.class,
			() -> updatePasswordInteractor.updatePassword(USER_ID, request));
		assertEquals(UserError.SAME_PASSWORD_NOT_ALLOWED, exception.getError());
		verify(userRepository).findById(USER_ID);
		verifyNoInteractions(passwordEncoder);
	}

	@Test
	@DisplayName("잘못된 이전 비밀번호 테스트")
	void updatePassword_WrongOldPassword() {
		// given
		UpdatePasswordRequest request = new UpdatePasswordRequest(OLD_PASSWORD, NEW_PASSWORD);
		when(userRepository.findById(USER_ID)).thenReturn(Optional.of(mockUser));
		when(mockUser.getPassword()).thenReturn(ENCODED_PASSWORD);
		when(passwordEncoder.matches(OLD_PASSWORD, ENCODED_PASSWORD)).thenReturn(false);

		// when & then
		DomainException exception = assertThrows(DomainException.class,
			() -> updatePasswordInteractor.updatePassword(USER_ID, request));
		assertEquals(UserError.WRONG_PASSWORD, exception.getError());
		verify(userRepository).findById(USER_ID);
		verify(passwordEncoder).matches(OLD_PASSWORD, ENCODED_PASSWORD);
	}

	@Test
	@DisplayName("유효하지 않은 새 비밀번호 형식 테스트")
	void updatePassword_InvalidNewPassword() {
		// given
		String invalidNewPassword = "weak";
		UpdatePasswordRequest request = new UpdatePasswordRequest(OLD_PASSWORD, invalidNewPassword);
		when(userRepository.findById(USER_ID)).thenReturn(Optional.of(mockUser));
		when(mockUser.getPassword()).thenReturn(ENCODED_PASSWORD);
		when(passwordEncoder.matches(OLD_PASSWORD, ENCODED_PASSWORD)).thenReturn(true);

		// when & then
		assertThrows(DomainException.class,
			() -> updatePasswordInteractor.updatePassword(USER_ID, request));
		verify(userRepository).findById(USER_ID);
		verify(passwordEncoder).matches(OLD_PASSWORD, ENCODED_PASSWORD);
	}
}