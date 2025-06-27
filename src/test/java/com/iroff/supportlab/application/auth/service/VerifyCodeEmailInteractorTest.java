package com.iroff.supportlab.application.auth.service;

import com.iroff.supportlab.application.auth.dto.VerifyCodeEmailRequest;
import com.iroff.supportlab.application.auth.dto.VerifyCodeResponse;
import com.iroff.supportlab.application.auth.dto.vo.VerifyCodeResponseStatus;
import com.iroff.supportlab.domain.auth.model.vo.VerificationType;
import com.iroff.supportlab.domain.auth.port.in.exception.AuthError;
import com.iroff.supportlab.domain.auth.port.out.VerificationCodeRepository;
import com.iroff.supportlab.domain.auth.port.out.VerificationStateRepository;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VerifyCodeEmailInteractorTest {

    @Mock
    private VerificationCodeRepository codeRepository;

    @Mock
    private VerificationStateRepository stateRepository;

    @InjectMocks
    private VerifyCodeEmailInteractor verifyCodeEmailInteractor;

    private VerifyCodeEmailRequest verifyCodeEmailRequest;

    @BeforeEach
    void setUp() {
        verifyCodeEmailRequest = new VerifyCodeEmailRequest("test@example.com", "123456");
    }

    @Test
    @DisplayName("이메일 코드 검증 성공")
    void verifyCodeEmail_success() {
        when(codeRepository.find(any(VerificationType.class), anyString())).thenReturn(Optional.of("123456"));

        VerifyCodeResponse response = verifyCodeEmailInteractor.verifyCodeEmail(verifyCodeEmailRequest);

        assertEquals(VerifyCodeResponseStatus.SUCCESS, response.status());
    }

    @Test
    @DisplayName("이메일 코드 검증 실패 - 코드가 존재하지 않음")
    void verifyCodeEmail_fail_codeNotExists() {
        when(codeRepository.find(any(VerificationType.class), anyString())).thenReturn(Optional.empty());

        DomainException exception = assertThrows(DomainException.class, () -> {
            verifyCodeEmailInteractor.verifyCodeEmail(verifyCodeEmailRequest);
        });

        assertEquals(AuthError.CODE_NOT_EXISTS, exception.getError());
    }

    @Test
    @DisplayName("이메일 코드 검증 실패 - 코드가 일치하지 않음")
    void verifyCodeEmail_fail_verifyCodeFailed() {
        when(codeRepository.find(any(VerificationType.class), anyString())).thenReturn(Optional.of("654321"));

        DomainException exception = assertThrows(DomainException.class, () -> {
            verifyCodeEmailInteractor.verifyCodeEmail(verifyCodeEmailRequest);
        });

        assertEquals(AuthError.VERIFY_CODE_FAILED, exception.getError());
    }
} 