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

import com.iroff.supportlab.application.user.dto.UpdateMarketingAgreedRequest;
import com.iroff.supportlab.domain.auth.model.vo.VerificationType;
import com.iroff.supportlab.domain.auth.port.in.exception.AuthError;
import com.iroff.supportlab.domain.auth.port.out.VerificationStateRepository;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.user.model.User;
import com.iroff.supportlab.domain.user.port.in.exception.UserError;
import com.iroff.supportlab.domain.user.port.out.UserRepository;

@ExtendWith(MockitoExtension.class)
class UpdateMarketingAgreedInteractorTest {

	private final Long USER_ID = 1L;

	@Mock
	private UserRepository userRepository;

	@Mock
	private VerificationStateRepository stateRepository;

	@Mock
	private User mockUser;

	@InjectMocks
	private UpdateMarketingAgreedInteractor updateMarketingAgreedInteractor;

	@Test
	@DisplayName("마케팅 수신 동의로 업데이트 성공 테스트")
	void updateMarketingAgreed_AgreeSuccess() {
		// given
		UpdateMarketingAgreedRequest request = new UpdateMarketingAgreedRequest(true);
		when(userRepository.findById(USER_ID)).thenReturn(Optional.of(mockUser));
		when(stateRepository.isVerifiedByUser(VerificationType.USER_INFO_MODIFICATION_VERIFIED, USER_ID.toString(),
			USER_ID)).thenReturn(true);
		doNothing().when(mockUser).changeMarketingAgreed(true);

		// when
		assertDoesNotThrow(() -> updateMarketingAgreedInteractor.updateMarketingAgreed(USER_ID, request));

		// then
		verify(userRepository).findById(USER_ID);
		verify(mockUser).changeMarketingAgreed(true);
	}

	@Test
	@DisplayName("마케팅 수신 거부로 업데이트 성공 테스트")
	void updateMarketingAgreed_DisagreeSuccess() {
		// given
		UpdateMarketingAgreedRequest request = new UpdateMarketingAgreedRequest(false);
		when(userRepository.findById(USER_ID)).thenReturn(Optional.of(mockUser));
		when(stateRepository.isVerifiedByUser(VerificationType.USER_INFO_MODIFICATION_VERIFIED, USER_ID.toString(),
			USER_ID)).thenReturn(true);
		doNothing().when(mockUser).changeMarketingAgreed(false);

		// when
		assertDoesNotThrow(() -> updateMarketingAgreedInteractor.updateMarketingAgreed(USER_ID, request));

		// then
		verify(userRepository).findById(USER_ID);
		verify(mockUser).changeMarketingAgreed(false);
	}

	@Test
	@DisplayName("존재하지 않는 사용자 테스트")
	void updateMarketingAgreed_UserNotFound() {
		// given
		UpdateMarketingAgreedRequest request = new UpdateMarketingAgreedRequest(true);
		when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());
		when(stateRepository.isVerifiedByUser(VerificationType.USER_INFO_MODIFICATION_VERIFIED, USER_ID.toString(),
			USER_ID)).thenReturn(true);

		// when & then
		DomainException exception = assertThrows(DomainException.class,
			() -> updateMarketingAgreedInteractor.updateMarketingAgreed(USER_ID, request));

		assertEquals(UserError.USER_NOT_FOUND, exception.getError());
		verify(userRepository).findById(USER_ID);
		verifyNoInteractions(mockUser);
	}

	@Test
	@DisplayName("비밀번호 인증을 하지 않은 상태로 업데이트 시도 테스트")
	void updateMarketingAgreed_UpdateWithNoVerifyingPassword() {
		// given
		when(stateRepository.isVerifiedByUser(VerificationType.USER_INFO_MODIFICATION_VERIFIED, USER_ID.toString(),
			USER_ID)).thenReturn(false);

		// when & then
		DomainException exception = assertThrows(DomainException.class,
			() -> updateMarketingAgreedInteractor.updateMarketingAgreed(USER_ID,
				new UpdateMarketingAgreedRequest(true)));

		assertEquals(AuthError.INVALID_AUTHORIZATION, exception.getError());
		verifyNoInteractions(mockUser);
		verify(userRepository, never()).findById(USER_ID);
		verify(mockUser, never()).changeMarketingAgreed(anyBoolean());
		verify(userRepository, never()).save(mockUser);
	}
}