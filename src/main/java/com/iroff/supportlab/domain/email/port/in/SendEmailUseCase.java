package com.iroff.supportlab.domain.email.port.in;

public interface SendEmailUseCase {
    void sendEmail(String to, String subject, String content);
} 