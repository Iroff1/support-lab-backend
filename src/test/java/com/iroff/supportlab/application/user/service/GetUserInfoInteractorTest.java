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

import com.iroff.supportlab.application.user.dto.GetUserInfoResponse;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.user.model.Role;
import com.iroff.supportlab.domain.user.model.User;
import com.iroff.supportlab.domain.user.port.in.exception.UserError;
import com.iroff.supportlab.domain.user.port.out.UserRepository;

@ExtendWith(MockitoExtension.class)
class GetUserInfoInteractorTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private GetUserInfoInteractor getUserInfoInteractor;

	private User user;

	@BeforeEach
	void setUp() {
		user = User.builder()
			.id(1L)
			.name("Test User")
			.email("test@example.com")
			.phone("01012345678")
			.role(Role.USER)
			.termsOfServiceAgreed(true)
			.privacyPolicyAgreed(true)
			.marketingAgreed(false)
			.build();
	}

	@Test
	@DisplayName("사용자 정보 조회 성공")
	void getUserInfo_success() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

		GetUserInfoResponse response = getUserInfoInteractor.getUserInfo(1L);

		assertEquals(user.getName(), response.name());
		assertEquals(user.getEmail(), response.email());
		assertEquals(user.getPhone(), response.phone());
	}

	@Test
	@DisplayName("사용자 정보 조회 실패 - 사용자를 찾을 수 없음")
	void getUserInfo_fail_userNotFound() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

		DomainException exception = assertThrows(DomainException.class, () -> {
			getUserInfoInteractor.getUserInfo(1L);
		});

		assertEquals(UserError.USER_NOT_FOUND, exception.getError());
	}
}
 