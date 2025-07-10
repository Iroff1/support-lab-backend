package com.iroff.supportlab.application.auth.service;

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
import org.springframework.security.crypto.password.PasswordEncoder;

import com.iroff.supportlab.application.auth.dto.LoginRequest;
import com.iroff.supportlab.application.auth.dto.LoginResponse;
import com.iroff.supportlab.domain.user.model.Role;
import com.iroff.supportlab.domain.user.model.User;
import com.iroff.supportlab.domain.user.port.out.UserRepository;
import com.iroff.supportlab.framework.config.security.JwtTokenProvider;

@ExtendWith(MockitoExtension.class)
class LoginInteractorTest {

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private JwtTokenProvider jwtTokenProvider;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private LoginInteractor loginInteractor;

	private User user;
	private LoginRequest loginRequest;

	@BeforeEach
	void setUp() {
		user = User.builder()
			.id(1L)
			.email("test@example.com")
			.password("encodedPassword")
			.role(Role.USER)
			.build();

		loginRequest = new LoginRequest("test@example.com", "password");
	}

	@Test
	@DisplayName("로그인 성공")
	void login_success() {
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
		when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
		when(jwtTokenProvider.createToken(any(Long.class), anyString())).thenReturn("testToken");

		LoginResponse response = loginInteractor.login(loginRequest);

		assertNotNull(response);
		assertEquals("testToken", response.accessToken());
	}

	@Test
	@DisplayName("로그인 실패 - 존재하지 않는 이메일")
	void login_fail_invalidEmail() {
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

		assertThrows(IllegalArgumentException.class, () -> {
			loginInteractor.login(loginRequest);
		});
	}

	@Test
	@DisplayName("로그인 실패 - 잘못된 비밀번호")
	void login_fail_invalidPassword() {
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
		when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

		assertThrows(IllegalArgumentException.class, () -> {
			loginInteractor.login(loginRequest);
		});
	}
} 