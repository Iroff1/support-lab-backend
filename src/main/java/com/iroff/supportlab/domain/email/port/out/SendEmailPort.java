package com.iroff.supportlab.domain.email.port.out;

public interface SendEmailPort {
    void sendEmail(String to, String subject, String content);
} 