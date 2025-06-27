package com.iroff.supportlab.application.auth.service;

import com.iroff.supportlab.adapter.auth.out.config.SmsProperties;
import com.iroff.supportlab.application.auth.dto.SendCodeEmailRequest;
import com.iroff.supportlab.domain.auth.port.in.exception.AuthError;
import com.iroff.supportlab.domain.auth.port.out.RateLimiter;
import com.iroff.supportlab.domain.auth.port.out.VerificationCodeRepository;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.email.port.out.SendEmailPort;
import com.iroff.supportlab.framework.config.email.EmailConfig;
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
class SendCodeEmailInteractorTest {

    @Mock
    private RateLimiter rateLimiter;
    @Mock
    private VerifyCodeGenerator verifyCodeGenerator;
    @Mock
    private VerificationCodeRepository verificationCodeRepository;
    @Mock
    private SendEmailPort sendEmailPort;
    @Mock
    private SmsProperties smsProperties;
    @Mock
    private EmailConfig emailConfig;

    @InjectMocks
    private SendCodeEmailInteractor sendCodeEmailInteractor;

    private SendCodeEmailRequest sendCodeEmailRequest;
    private final String ip = "127.0.0.1";

    @BeforeEach
    void setUp() {
        sendCodeEmailRequest = new SendCodeEmailRequest("test@example.com");
    }

    @Test
    @DisplayName("이메일 인증코드 전송 성공")
    void sendCodeEmail_success() {
        when(rateLimiter.tryAcquire(anyString())).thenReturn(true);
        when(verifyCodeGenerator.generateCode()).thenReturn("123456");
        when(smsProperties.getAuthTemplate()).thenReturn("인증번호 입니다.");
        when(emailConfig.getCodeSubject()).thenReturn("인증코드");

        sendCodeEmailInteractor.sendCodeEmail(sendCodeEmailRequest, ip);

        verify(sendEmailPort).sendEmail(anyString(), anyString(), anyString());
        verify(verificationCodeRepository).save(any(), anyString(), anyString(), any(Duration.class));
    }

    @Test
    @DisplayName("이메일 인증코드 전송 실패 - 너무 많은 요청")
    void sendCodeEmail_fail_tooManyRequests() {
        when(rateLimiter.tryAcquire(anyString())).thenReturn(false);

        DomainException exception = assertThrows(DomainException.class, () -> {
            sendCodeEmailInteractor.sendCodeEmail(sendCodeEmailRequest, ip);
        });

        assertEquals(AuthError.TOO_MANY_REQUESTS, exception.getError());
    }
} 