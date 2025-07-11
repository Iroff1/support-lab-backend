package com.iroff.supportlab.application.auth.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.anyString;

import java.time.Duration;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iroff.supportlab.domain.auth.port.in.exception.AuthError;
import com.iroff.supportlab.domain.auth.port.out.BlacklistRepository;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.framework.config.security.JwtTokenProvider;

@ExtendWith(MockitoExtension.class)
class LogoutInteractorTest {

	@Mock
	private BlacklistRepository blacklistRepository;

	@Mock
	private JwtTokenProvider jwtTokenProvider;

	@InjectMocks
	private LogoutInteractor logoutInteractor;

	@Test
	@DisplayName("로그아웃 성공 - 'Bearer ' 접두사가 있는 토큰")
	void logout_success_withBearerPrefix() {
		// given
		String accessToken = "Bearer testAccessToken";
		String pureToken = "testAccessToken";
		long now = System.currentTimeMillis();
		long expirationTime = now + 3600 * 1000; // 1시간 후 만료
		Date expirationDate = new Date(expirationTime);

		when(jwtTokenProvider.validateToken(pureToken)).thenReturn(true);
		when(jwtTokenProvider.getExpirationDateFromToken(pureToken)).thenReturn(expirationDate);

		// when
		logoutInteractor.logout(accessToken);

		// then
		verify(blacklistRepository).save(eq(pureToken), any(Duration.class));
	}

	@Test
	@DisplayName("로그아웃 실패 - 'Bearer ' 접두사 없는 토큰")
	void logout_success_withoutBearerPrefix() {
		// given
		String pureToken = "testAccessToken";
		long now = System.currentTimeMillis();
		long expirationTime = now + 3600 * 1000;
		Date expirationDate = new Date(expirationTime);

		// when
		DomainException exception = assertThrows(DomainException.class, () -> {
			logoutInteractor.logout(pureToken);
		});

		// then
		assertEquals(AuthError.LOGOUT_FAILED, exception.getError());
		verify(blacklistRepository, never()).save(anyString(), any(Duration.class));
	}

	@Test
	@DisplayName("로그아웃 실패 - 유효하지 않은 토큰")
	void logout_fail_invalidToken() {
		// given
		String accessToken = "Bearer invalidToken";
		String pureToken = "invalidToken";

		when(jwtTokenProvider.validateToken(pureToken)).thenReturn(false);

		// when
		DomainException exception = assertThrows(DomainException.class, () -> {
			logoutInteractor.logout(accessToken);
		});

		// then
		assertEquals(AuthError.LOGOUT_FAILED, exception.getError());
		verify(blacklistRepository, never()).save(anyString(), any(Duration.class));
	}

	@Test
	@DisplayName("로그아웃 처리 안함 - 이미 만료된 토큰")
	void logout_doNothing_expiredToken() {
		// given
		String accessToken = "Bearer expiredToken";
		String pureToken = "expiredToken";
		long now = System.currentTimeMillis();
		long expirationTime = now - 1000; // 1초 전 만료
		Date expirationDate = new Date(expirationTime);

		when(jwtTokenProvider.validateToken(pureToken)).thenReturn(true);
		when(jwtTokenProvider.getExpirationDateFromToken(pureToken)).thenReturn(expirationDate);

		// when
		DomainException exception = assertThrows(DomainException.class, () -> {
			logoutInteractor.logout(accessToken);
		});

		// then
		assertEquals(AuthError.EXPIRED_TOKEN, exception.getError());
		verify(blacklistRepository, never()).save(anyString(), any(Duration.class));
	}
}
