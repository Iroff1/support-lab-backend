package com.iroff.supportlab.framework.config.auth;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.iroff.supportlab.adapter.auth.out.NcpSmsClient;
import com.iroff.supportlab.adapter.auth.out.config.NcpProperties;
import com.iroff.supportlab.adapter.auth.out.config.SmsProperties;
import com.iroff.supportlab.domain.auth.port.out.SmsClient;

@Configuration
public class SmsConfig {
	@Bean
	@ConditionalOnProperty(name = "SMS_PROVIDER", havingValue = "ncp")
	SmsClient ncpSmsClient(SmsProperties smsProperties, NcpProperties ncpProperties) {
		return new NcpSmsClient(smsProperties, ncpProperties);
	}
}
