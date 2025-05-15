package com.iroff.supportlab.adapter.auth.out.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
@ConfigurationProperties(prefix = "sms")
public class SmsProperties {
	@Value("${sms.template.auth}")
	private String authTemplate;

	@Value("${sms.limit.amount}")
	private Long limitAmount;

	@Value("${sms.limit.duration-minutes}")
	private Integer limitDurationMinutes;
}
