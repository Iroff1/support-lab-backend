package com.iroff.supportlab.framework.config.payment;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class TossPaymentsConfig {

	@Value("${payment.toss.secret-key}")
	private String secretKey;

	@Value("${payment.toss.client-key}")
	private String clientKey;

	@Value("${payment.toss.secure-key}")
	private String secureKey;

	@Bean
	public WebClient tossWebClient() {
		byte[] encodedSecretKey = Base64.getEncoder()
			.encode((secretKey + ":").getBytes(StandardCharsets.UTF_8));
		return WebClient.builder()
			.baseUrl("https://api.tosspayments.com")
			.defaultHeader("Authorization", "Basic " + new String(encodedSecretKey))
			.defaultHeader("Content-Type", "application/json")
			.build();
	}
}