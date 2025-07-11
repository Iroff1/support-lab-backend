package com.iroff.supportlab.application.auth.service;

import java.time.Duration;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.iroff.supportlab.domain.auth.port.in.LogoutUseCase;
import com.iroff.supportlab.domain.auth.port.in.exception.AuthError;
import com.iroff.supportlab.domain.auth.port.out.BlacklistRepository;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.common.port.in.exception.ErrorInfo;
import com.iroff.supportlab.framework.config.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogoutInteractor implements LogoutUseCase {

	private final BlacklistRepository blacklistRepository;
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public void logout(String accessToken) {

		checkCondition(accessToken != null && accessToken.startsWith("Bearer "), AuthError.LOGOUT_FAILED);
		accessToken = accessToken.substring(7);
		checkCondition(!accessToken.isEmpty(), AuthError.LOGOUT_FAILED);
		checkCondition(jwtTokenProvider.validateToken(accessToken), AuthError.LOGOUT_FAILED);

		Date expiration = jwtTokenProvider.getExpirationDateFromToken(accessToken);
		long remainingMillis = expiration.getTime() - System.currentTimeMillis();
		checkCondition(remainingMillis > 0, AuthError.EXPIRED_TOKEN);
		blacklistRepository.save(accessToken, Duration.ofMillis(remainingMillis));
	}

	void checkCondition(boolean condition, ErrorInfo errorInfo) {
		if (!condition) {
			throw new DomainException(errorInfo);
		}
	}
}