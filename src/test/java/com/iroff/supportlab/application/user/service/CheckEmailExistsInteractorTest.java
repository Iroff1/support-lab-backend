package com.iroff.supportlab.application.user.service;

import com.iroff.supportlab.adapter.user.out.persistence.UserEntity;
import com.iroff.supportlab.application.user.dto.CheckEmailExistsResponse;
import com.iroff.supportlab.domain.user.port.out.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckEmailExistsInteractorTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CheckEmailExistsInteractor checkEmailExistsInteractor;

    @Test
    @DisplayName("이메일 존재 확인 - 존재함")
    void checkEmailExists_true() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(UserEntity.builder().build()));

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