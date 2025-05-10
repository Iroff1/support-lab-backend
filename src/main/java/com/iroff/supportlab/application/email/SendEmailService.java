package com.iroff.supportlab.application.email;

import com.iroff.supportlab.domain.email.port.in.SendEmailUseCase;
import com.iroff.supportlab.domain.email.port.out.SendEmailPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendEmailService implements SendEmailUseCase {

    private final SendEmailPort sendEmailPort;

    @Override
    public void sendEmail(String to, String subject, String content) {
        sendEmailPort.sendEmail(to, subject, content);
    }
} 