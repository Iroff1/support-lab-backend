package com.iroff.supportlab.application.user.service;

import com.iroff.supportlab.adapter.user.out.persistence.UserEntity;
import com.iroff.supportlab.application.user.dto.DeleteUserRequest;
import com.iroff.supportlab.domain.auth.port.in.exception.AuthError;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.user.model.Role;
import com.iroff.supportlab.domain.user.port.in.exception.UserError;
import com.iroff.supportlab.domain.user.port.out.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteUserInteractorTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DeleteUserInteractor deleteUserInteractor;

    private UserEntity userEntity;
    private DeleteUserRequest deleteUserRequest;

    @BeforeEach
    void setUp() {
        userEntity = UserEntity.builder()
                .id(1L)
                .email("test@example.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();
        deleteUserRequest = new DeleteUserRequest("password");
    }

    @Test
    @DisplayName("사용자 삭제 성공")
    void deleteUser_success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        deleteUserInteractor.deleteUser(1L, deleteUserRequest);

        verify(userRepository).deleteById(1L);
    }

    @Test
    @DisplayName("사용자 삭제 실패 - 사용자를 찾을 수 없음")
    void deleteUser_fail_userNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        DomainException exception = assertThrows(DomainException.class, () -> {
            deleteUserInteractor.deleteUser(1L, deleteUserRequest);
        });

        assertEquals(UserError.USER_NOT_FOUND, exception.getError());
    }

    @Test
    @DisplayName("사용자 삭제 실패 - 잘못된 비밀번호")
    void deleteUser_fail_invalidPassword() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        DomainException exception = assertThrows(DomainException.class, () -> {
            deleteUserInteractor.deleteUser(1L, deleteUserRequest);
        });

        assertEquals(AuthError.INVALID_PASSWORD, exception.getError());
    }
} 