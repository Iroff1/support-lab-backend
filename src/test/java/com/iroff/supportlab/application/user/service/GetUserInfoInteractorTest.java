package com.iroff.supportlab.application.user.service;

import com.iroff.supportlab.adapter.user.out.persistence.UserEntity;
import com.iroff.supportlab.application.user.dto.GetUserInfoResponse;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserInfoInteractorTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserInfoInteractor getUserInfoInteractor;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userEntity = UserEntity.builder()
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
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));

        GetUserInfoResponse response = getUserInfoInteractor.getUserInfo(1L);

        assertEquals(userEntity.getName(), response.name());
        assertEquals(userEntity.getEmail(), response.email());
        assertEquals(userEntity.getPhone(), response.phone());
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