package com.iroff.supportlab.application.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iroff.supportlab.application.user.dto.CheckEmailExistsResponse;
import com.iroff.supportlab.domain.user.model.User;
import com.iroff.supportlab.domain.user.port.out.UserRepository;

@ExtendWith(MockitoExtension.class)
class CheckEmailExistsInteractorTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private CheckEmailExistsInteractor checkEmailExistsInteractor;

	@Test
	@DisplayName("이메일 존재 확인 - 존재함")
	void checkEmailExists_true() {
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(User.builder().build()));

		CheckEmailExistsResponse response = checkEmailExistsInteractor.checkEmailExists("test@example.com");

		assertTrue(response.exists());
	}

	@Test
	@DisplayName("이메일 존재 확인 - 존재하지 않음")
	void checkEmailExists_false() {
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

		CheckEmailExistsResponse response = checkEmailExistsInteractor.checkEmailExists("test@example.com");

		assertFalse(response.exists());
	}
} 