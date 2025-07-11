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

import com.iroff.supportlab.application.user.dto.FindEmailRequest;
import com.iroff.supportlab.application.user.dto.FindEmailResponse;
import com.iroff.supportlab.domain.auth.model.vo.VerificationType;
import com.iroff.supportlab.domain.auth.port.in.exception.AuthError;
import com.iroff.supportlab.domain.auth.port.out.VerificationStateRepository;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.user.model.User;
import com.iroff.supportlab.domain.user.port.out.UserRepository;

@ExtendWith(MockitoExtension.class)
class FindEmailInteractorTest {

	@Mock
	private UserRepository userRepository;
	@Mock
	private VerificationStateRepository verificationStateRepository;

	@InjectMocks
	private FindEmailInteractor findEmailInteractor;

	private FindEmailRequest findEmailRequest;
	private User user;

	@BeforeEach
	void setUp() {
		findEmailRequest = new FindEmailRequest("Test User", "01012345678");
		user = User.builder()
			.name("Test User")
			.email("test@example.com")
			.phone("01012345678")
			.build();
	}

	@Test
	@DisplayName("이메일 찾기 성공")
	void findEmail_success() {
		when(verificationStateRepository.isVerified(any(VerificationType.class), anyString())).thenReturn(true);
		when(userRepository.findByPhone(anyString())).thenReturn(Optional.of(user));

		FindEmailResponse response = findEmailInteractor.findEmail(findEmailRequest);

		assertEquals("test@example.com", response.email());
	}

	@Test
	@DisplayName("이메일 찾기 실패 - 인증 실패")
	void findEmail_fail_verificationFailed() {
		when(verificationStateRepository.isVerified(any(VerificationType.class), anyString())).thenReturn(false);

		DomainException exception = assertThrows(DomainException.class, () -> {
			findEmailInteractor.findEmail(findEmailRequest);
		});
		assertEquals(AuthError.VERIFY_CODE_FAILED, exception.getError());
	}

	@Test
	@DisplayName("이메일 찾기 실패 - 사용자 정보 불일치")
	void findEmail_fail_userInfoMismatch() {
		when(verificationStateRepository.isVerified(any(VerificationType.class), anyString())).thenReturn(true);
		User mismatchedUser = User.builder().name("Wrong User").build();
		when(userRepository.findByPhone(anyString())).thenReturn(Optional.of(mismatchedUser));

		FindEmailResponse response = findEmailInteractor.findEmail(findEmailRequest);

		assertNull(response.email());
	}

	@Test
	@DisplayName("이메일 찾기 실패 - 사용자가 존재하지 않음")
	void findEmail_fail_userNotFound() {
		when(verificationStateRepository.isVerified(any(VerificationType.class), anyString())).thenReturn(true);
		when(userRepository.findByPhone(anyString())).thenReturn(Optional.empty());

		FindEmailResponse response = findEmailInteractor.findEmail(findEmailRequest);

		assertNull(response.email());
	}
} 