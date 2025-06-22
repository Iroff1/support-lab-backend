package com.iroff.supportlab.application.auth.service;

import com.iroff.supportlab.application.auth.dto.SendCodeRequest;
import com.iroff.supportlab.domain.auth.model.vo.VerificationType;
import com.iroff.supportlab.domain.auth.port.in.exception.AuthError;
import com.iroff.supportlab.domain.auth.port.out.RateLimiter;
import com.iroff.supportlab.domain.auth.port.out.SmsClient;
import com.iroff.supportlab.domain.auth.port.out.VerificationCodeRepository;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SendCodeInteractorTest {

    @Mock
    private SmsClient smsClient;
    @Mock
    private VerifyCodeGenerator verifyCodeGenerator;
    @Mock
    private RateLimiter rateLimiter;
    @Mock
    private VerificationCodeRepository verificationCodeRepository;

    @InjectMocks
    private SendCodeInteractor sendCodeInteractor;

    private SendCodeRequest sendCodeRequest;
    private final String ip = "127.0.0.1";

    @BeforeEach
    void setUp() {
        sendCodeRequest = new SendCodeRequest(VerificationType.SIGN_UP_CODE, "01012345678");
    }

    @Test
    @DisplayName("SMS 인증코드 전송 성공")
    void sendCode_success() {
        when(rateLimiter.tryAcquire(anyString())).thenReturn(true);
        when(verifyCodeGenerator.generateCode()).thenReturn("123456");

        sendCodeInteractor.sendCode(sendCodeRequest, ip);

        verify(smsClient).sendCode(anyString(), anyString());
        verify(verificationCodeRepository).save(any(VerificationType.class), anyString(), anyString(), any(Duration.class));
    }

    @Test
    @DisplayName("SMS 인증코드 전송 실패 - 너무 많은 요청")
    void sendCode_fail_tooManyRequests() {
        when(rateLimiter.tryAcquire(anyString())).thenReturn(false);

        DomainException exception = assertThrows(DomainException.class, () -> {
            sendCodeInteractor.sendCode(sendCodeRequest, ip);
        });

        assertEquals(AuthError.TOO_MANY_REQUESTS, exception.getError());
    }
} 