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

import com.iroff.supportlab.application.user.dto.UpdateNameRequest;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.user.model.User;
import com.iroff.supportlab.domain.user.port.in.exception.UserError;
import com.iroff.supportlab.domain.user.port.out.UserRepository;

@ExtendWith(MockitoExtension.class)
class UpdateNameInteractorTest {

	private final Long USER_ID = 1L;
	private final String VALID_NAME = "홍길동";
	private final String CURRENT_NAME = "김철수";
	@Mock
	private UserRepository userRepository;
	@Mock
	private User mockUser;
	@InjectMocks
	private UpdateNameInteractor updateNameInteractor;

	@Test
	@DisplayName("올바른 이름(한글 2~6자)으로 업데이트 성공 테스트")
	void updateName_Success() {
		// given
		UpdateNameRequest request = new UpdateNameRequest(VALID_NAME);
		when(userRepository.findById(USER_ID)).thenReturn(Optional.of(mockUser));
		when(mockUser.getName()).thenReturn(CURRENT_NAME);
		doNothing().when(mockUser).changeName(VALID_NAME);

		// when
		assertDoesNotThrow(() -> updateNameInteractor.updateName(USER_ID, request));

		// then
		verify(userRepository).findById(USER_ID);
		verify(mockUser).changeName(VALID_NAME);
	}

	@Test
	@DisplayName("존재하지 않는 사용자 테스트")
	void updateName_UserNotFound() {
		// given
		UpdateNameRequest request = new UpdateNameRequest(VALID_NAME);
		when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

		// when & then
		DomainException exception = assertThrows(DomainException.class,
			() -> updateNameInteractor.updateName(USER_ID, request));

		assertEquals(UserError.USER_NOT_FOUND, exception.getError());
		verify(userRepository).findById(USER_ID);
		verifyNoInteractions(mockUser);
	}

	@Test
	@DisplayName("이전과 같은 이름으로 업데이트 시도 테스트")
	void updateName_SameName() {
		// given
		String sameName = CURRENT_NAME;
		UpdateNameRequest request = new UpdateNameRequest(sameName);
		when(userRepository.findById(USER_ID)).thenReturn(Optional.of(mockUser));
		when(mockUser.getName()).thenReturn(CURRENT_NAME);

		// when & then
		DomainException exception = assertThrows(DomainException.class,
			() -> updateNameInteractor.updateName(USER_ID, request));

		assertEquals(UserError.SAME_NAME_NOT_ALLOWED, exception.getError());
		verify(userRepository).findById(USER_ID);
		verify(mockUser, never()).changeName(sameName);
	}

	@Test
	@DisplayName("한글자 이름으로 업데이트 시도 테스트")
	void updateName_SingleCharacterName() {
		// given
		String singleCharName = "홍";
		UpdateNameRequest request = new UpdateNameRequest(singleCharName);
		when(userRepository.findById(USER_ID)).thenReturn(Optional.of(mockUser));
		when(mockUser.getName()).thenReturn(CURRENT_NAME);

		// when & then
		DomainException exception = assertThrows(DomainException.class,
			() -> updateNameInteractor.updateName(USER_ID, request));

		assertEquals(UserError.INVALID_NAME, exception.getError());
		verify(userRepository).findById(USER_ID);
		verify(mockUser, never()).changeName(singleCharName);
	}

	@Test
	@DisplayName("7자 이상 이름으로 업데이트 시도 테스트")
	void updateName_TooLongName() {
		// given
		String longName = "홍길동일이삼사";  // 7글자
		UpdateNameRequest request = new UpdateNameRequest(longName);
		when(userRepository.findById(USER_ID)).thenReturn(Optional.of(mockUser));
		when(mockUser.getName()).thenReturn(CURRENT_NAME);

		// when & then
		DomainException exception = assertThrows(DomainException.class,
			() -> updateNameInteractor.updateName(USER_ID, request));

		assertEquals(UserError.INVALID_NAME, exception.getError());
		verify(userRepository).findById(USER_ID);
		verify(mockUser, never()).changeName(longName);
	}

	@Test
	@DisplayName("영문 이름으로 업데이트 시도 테스트")
	void updateName_EnglishName() {
		// given
		String englishName = "Hong";
		UpdateNameRequest request = new UpdateNameRequest(englishName);
		when(userRepository.findById(USER_ID)).thenReturn(Optional.of(mockUser));
		when(mockUser.getName()).thenReturn(CURRENT_NAME);

		// when & then
		DomainException exception = assertThrows(DomainException.class,
			() -> updateNameInteractor.updateName(USER_ID, request));

		assertEquals(UserError.INVALID_NAME, exception.getError());
		verify(userRepository).findById(USER_ID);
		verify(mockUser, never()).changeName(englishName);
	}

	@Test
	@DisplayName("특수문자가 포함된 이름으로 업데이트 시도 테스트")
	void updateName_SpecialCharacters() {
		// given
		String nameWithSpecialChar = "홍길★동";
		UpdateNameRequest request = new UpdateNameRequest(nameWithSpecialChar);
		when(userRepository.findById(USER_ID)).thenReturn(Optional.of(mockUser));
		when(mockUser.getName()).thenReturn(CURRENT_NAME);

		// when & then
		DomainException exception = assertThrows(DomainException.class,
			() -> updateNameInteractor.updateName(USER_ID, request));

		assertEquals(UserError.INVALID_NAME, exception.getError());
		verify(userRepository).findById(USER_ID);
		verify(mockUser, never()).changeName(nameWithSpecialChar);
	}

	@Test
	@DisplayName("빈 문자열로 업데이트 시도 테스트")
	void updateName_EmptyString() {
		// given
		String emptyName = "";
		UpdateNameRequest request = new UpdateNameRequest(emptyName);
		when(userRepository.findById(USER_ID)).thenReturn(Optional.of(mockUser));
		when(mockUser.getName()).thenReturn(CURRENT_NAME);

		// when & then
		DomainException exception = assertThrows(DomainException.class,
			() -> updateNameInteractor.updateName(USER_ID, request));

		assertEquals(UserError.INVALID_NAME, exception.getError());
		verify(userRepository).findById(USER_ID);
		verify(mockUser, never()).changeName(emptyName);
	}
}