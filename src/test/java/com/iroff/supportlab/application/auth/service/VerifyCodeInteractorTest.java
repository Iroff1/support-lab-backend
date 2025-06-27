package com.iroff.supportlab.application.auth.service;

import com.iroff.supportlab.application.auth.dto.VerifyCodeRequest;
import com.iroff.supportlab.application.auth.dto.VerifyCodeResponse;
import com.iroff.supportlab.application.auth.dto.vo.VerifyCodeResponseStatus;
import com.iroff.supportlab.domain.auth.model.vo.VerificationType;
import com.iroff.supportlab.domain.auth.port.in.exception.AuthError;
import com.iroff.supportlab.domain.auth.port.out.VerificationCodeRepository;
import com.iroff.supportlab.domain.auth.port.out.VerificationStateRepository;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VerifyCodeInteractorTest {

    @Mock
    private VerificationCodeRepository codeRepository;

    @Mock
    private VerificationStateRepository stateRepository;

    @InjectMocks
    private VerifyCodeInteractor verifyCodeInteractor;

    @ParameterizedTest
    @EnumSource(value = VerificationType.class, names = {"SIGN_UP_CODE", "FIND_EMAIL_CODE", "FIND_PASSWORD_CODE"})
    @DisplayName("코드 검증 성공")
    void verifyCode_success(VerificationType type) {
        VerifyCodeRequest request = new VerifyCodeRequest(type, "01012345678", "123456");
        when(codeRepository.find(any(VerificationType.class), anyString())).thenReturn(Optional.of("123456"));

        VerifyCodeResponse response = verifyCodeInteractor.verifyCode(request);

        assertEquals(VerifyCodeResponseStatus.SUCCESS, response.status());
    }

    @Test
    @DisplayName("코드 검증 실패 - 코드가 존재하지 않음")
    void verifyCode_fail_codeNotExists() {
        VerifyCodeRequest request = new VerifyCodeRequest(VerificationType.SIGN_UP_CODE, "01012345678", "123456");
        when(codeRepository.find(any(VerificationType.class), anyString())).thenReturn(Optional.empty());

        DomainException exception = assertThrows(DomainException.class, () -> {
            verifyCodeInteractor.verifyCode(request);
        });

        assertEquals(AuthError.CODE_NOT_EXISTS, exception.getError());
    }

    @Test
    @DisplayName("코드 검증 실패 - 코드가 일치하지 않음")
    void verifyCode_fail_verifyCodeFailed() {
        VerifyCodeRequest request = new VerifyCodeRequest(VerificationType.SIGN_UP_CODE, "01012345678", "123456");
        when(codeRepository.find(any(VerificationType.class), anyString())).thenReturn(Optional.of("654321"));

        DomainException exception = assertThrows(DomainException.class, () -> {
            verifyCodeInteractor.verifyCode(request);
        });

        assertEquals(AuthError.VERIFY_CODE_FAILED, exception.getError());
    }

    @Test
    @DisplayName("코드 검증 실패 - 잘못된 요청 타입")
    void verifyCode_fail_invalidRequest() {
        VerifyCodeRequest request = new VerifyCodeRequest(VerificationType.SIGN_UP_VERIFIED, "01012345678", "123456");
        when(codeRepository.find(any(VerificationType.class), anyString())).thenReturn(Optional.of("123456"));

        DomainException exception = assertThrows(DomainException.class, () -> {
            verifyCodeInteractor.verifyCode(request);
        });

        assertEquals(AuthError.INVALID_REQUEST, exception.getError());
    }
} 