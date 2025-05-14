package com.iroff.supportlab.adapter.auth.out.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "sms")
public class SmsProperties {
	@Value("${sms.template.auth}")
	private String authTemplate;
}
