package com.iroff.supportlab.adapter.email.out.persistence;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.iroff.supportlab.domain.email.port.out.SendEmailPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GmailSendEmailAdapter implements SendEmailPort {

	private static final String SENDER = "plankit24";
	private final JavaMailSender mailSender;

	@Override
	public void sendEmail(String to, String subject, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(SENDER);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(content);
		mailSender.send(message);
	}
} 
