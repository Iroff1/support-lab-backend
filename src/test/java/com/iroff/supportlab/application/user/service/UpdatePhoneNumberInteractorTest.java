package com.iroff.supportlab.application.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iroff.supportlab.application.user.dto.UpdatePhoneNumberRequest;
import com.iroff.supportlab.domain.auth.model.vo.VerificationType;
import com.iroff.supportlab.domain.auth.port.in.exception.AuthError;
import com.iroff.supportlab.domain.auth.port.out.VerificationStateRepository;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.user.model.User;
import com.iroff.supportlab.domain.user.port.in.exception.UserError;
import com.iroff.supportlab.domain.user.port.out.UserRepository;

@ExtendWith(MockitoExtension.class)
class UpdatePhoneNumberInteractorTest {

	private final Long USER_ID = 1L;
	private final String CURRENT_PHONE = "01012345678";
	private final String NEW_PHONE = "01087654321";
	private final String OTHER_USER_PHONE = "01098765432";

	@Mock
	private UserRepository userRepository;

	@Mock
	private VerificationStateRepository verificationStateRepository;

	@Mock
	private User mockUser;

	@InjectMocks
	private UpdatePhoneNumberInteractor updatePhoneNumberInteractor;

	@Test
	@DisplayName("전화번호 업데이트 성공 테스트")
	void updatePhoneNumber_Success() {
		// given
		UpdatePhoneNumberRequest request = new UpdatePhoneNumberRequest(NEW_PHONE);
		when(userRepository.findById(USER_ID)).thenReturn(Optional.of(mockUser));
		when(verificationStateRepository.isVerifiedByUser(
			VerificationType.UPDATE_PHONE_VERIFIED,
			NEW_PHONE,
			USER_ID
		)).thenReturn(true);
		when(mockUser.getPhone()).thenReturn(CURRENT_PHONE);
		when(userRepository.existsByPhone(NEW_PHONE)).thenReturn(false);
		doNothing().when(mockUser).changePhone(NEW_PHONE);

		// when
		assertDoesNotThrow(() ->
			updatePhoneNumberInteractor.updatePhoneNumber(USER_ID, request));

		// then
		verify(userRepository).findById(USER_ID);
		verify(verificationStateRepository).isVerifiedByUser(
			VerificationType.UPDATE_PHONE_VERIFIED,
			NEW_PHONE,
			USER_ID
		);
		verify(mockUser).getPhone();
		verify(userRepository).existsByPhone(NEW_PHONE);
		verify(verificationStateRepository).remove(VerificationType.UPDATE_PHONE_VERIFIED, NEW_PHONE);
		verify(mockUser).changePhone(NEW_PHONE);

		// 검증 메소드 호출 순서 확인
		InOrder inOrder = inOrder(userRepository, verificationStateRepository, mockUser);
		inOrder.verify(userRepository).findById(USER_ID);
		inOrder.verify(verificationStateRepository).isVerifiedByUser(any(), any(), any());
		inOrder.verify(mockUser).getPhone();
		inOrder.verify(userRepository).existsByPhone(any());
		inOrder.verify(verificationStateRepository).remove(any(), any());
		inOrder.verify(mockUser).changePhone(any());
	}

	@Test
	@DisplayName("전화번호 검증 순서 테스트 - 인증 실패")
	void updatePhoneNumber_ValidationOrder_AuthorizationFailed() {
		// given
		UpdatePhoneNumberRequest request = new UpdatePhoneNumberRequest(NEW_PHONE);
		when(userRepository.findById(USER_ID)).thenReturn(Optional.of(mockUser));
		when(verificationStateRepository.isVerifiedByUser(
			VerificationType.UPDATE_PHONE_VERIFIED,
			NEW_PHONE,
			USER_ID
		)).thenReturn(false);

		// when & then
		DomainException exception = assertThrows(DomainException.class,
			() -> updatePhoneNumberInteractor.updatePhoneNumber(USER_ID, request));

		assertEquals(AuthError.INVALID_AUTHORIZATION, exception.getError());
		verify(userRepository).findById(USER_ID);
		verify(verificationStateRepository).isVerifiedByUser(
			VerificationType.UPDATE_PHONE_VERIFIED,
			NEW_PHONE,
			USER_ID
		);
		verify(mockUser, never()).getPhone();
		verify(userRepository, never()).existsByPhone(any());
		verify(verificationStateRepository, never()).remove(any(), any());
		verify(mockUser, never()).changePhone(any());
	}

	@Test
	@DisplayName("전화번호 검증 순서 테스트 - 동일 번호")
	void updatePhoneNumber_ValidationOrder_SameNumber() {
		// given
		UpdatePhoneNumberRequest request = new UpdatePhoneNumberRequest(NEW_PHONE);
		when(userRepository.findById(USER_ID)).thenReturn(Optional.of(mockUser));
		when(verificationStateRepository.isVerifiedByUser(
			VerificationType.UPDATE_PHONE_VERIFIED,
			NEW_PHONE,
			USER_ID
		)).thenReturn(true);
		when(mockUser.getPhone()).thenReturn(NEW_PHONE);

		// when & then
		DomainException exception = assertThrows(DomainException.class,
			() -> updatePhoneNumberInteractor.updatePhoneNumber(USER_ID, request));

		assertEquals(UserError.SAME_PHONE_NUMBER_NOT_ALLOWED, exception.getError());
		verify(userRepository).findById(USER_ID);
		verify(verificationStateRepository).isVerifiedByUser(
			VerificationType.UPDATE_PHONE_VERIFIED,
			NEW_PHONE,
			USER_ID
		);
		verify(mockUser).getPhone();
		verify(userRepository, never()).existsByPhone(any());
		verify(verificationStateRepository, never()).remove(any(), any());
		verify(mockUser, never()).changePhone(any());
	}

	@Test
	@DisplayName("다른 사용자가 인증한 전화번호로 업데이트 시도 테스트")
	void updatePhoneNumber_VerifiedByOtherUser() {
		// given
		String phoneVerifiedByOtherUser = OTHER_USER_PHONE;
		UpdatePhoneNumberRequest request = new UpdatePhoneNumberRequest(phoneVerifiedByOtherUser);

		when(userRepository.findById(USER_ID)).thenReturn(Optional.of(mockUser));
		when(verificationStateRepository.isVerifiedByUser(
			VerificationType.UPDATE_PHONE_VERIFIED,
			phoneVerifiedByOtherUser,
			USER_ID
		)).thenReturn(false); // 현재 사용자는 인증하지 않은 번호

		// when & then
		DomainException exception = assertThrows(DomainException.class,
			() -> updatePhoneNumberInteractor.updatePhoneNumber(USER_ID, request));

		assertEquals(AuthError.INVALID_AUTHORIZATION, exception.getError());

		// 검증 순서 확인
		InOrder inOrder = inOrder(userRepository, verificationStateRepository);
		inOrder.verify(userRepository).findById(USER_ID);
		inOrder.verify(verificationStateRepository).isVerifiedByUser(
			VerificationType.UPDATE_PHONE_VERIFIED,
			phoneVerifiedByOtherUser,
			USER_ID
		);

		// 이후 과정은 실행되지 않아야 함
		verify(mockUser, never()).getPhone();
		verify(userRepository, never()).existsByPhone(any());
		verify(verificationStateRepository, never()).remove(any(), any());
		verify(mockUser, never()).changePhone(any());
	}

	@Test
	@DisplayName("전화번호 검증 순서 테스트 - 전화번호 중복")
	void updatePhoneNumber_ValidationOrder_DuplicateNumber() {
		// given
		UpdatePhoneNumberRequest request = new UpdatePhoneNumberRequest(NEW_PHONE);
		when(userRepository.findById(USER_ID)).thenReturn(Optional.of(mockUser));
		when(verificationStateRepository.isVerifiedByUser(
			VerificationType.UPDATE_PHONE_VERIFIED,
			NEW_PHONE,
			USER_ID
		)).thenReturn(true);
		when(mockUser.getPhone()).thenReturn(CURRENT_PHONE);
		when(userRepository.existsByPhone(NEW_PHONE)).thenReturn(true);

		// when & then
		DomainException exception = assertThrows(DomainException.class,
			() -> updatePhoneNumberInteractor.updatePhoneNumber(USER_ID, request));

		assertEquals(UserError.PHONE_ALREADY_EXISTS, exception.getError());
		verify(userRepository).findById(USER_ID);
		verify(verificationStateRepository).isVerifiedByUser(
			VerificationType.UPDATE_PHONE_VERIFIED,
			NEW_PHONE,
			USER_ID
		);
		verify(mockUser).getPhone();
		verify(userRepository).existsByPhone(NEW_PHONE);
		verify(verificationStateRepository, never()).remove(any(), any());
		verify(mockUser, never()).changePhone(any());
	}
}