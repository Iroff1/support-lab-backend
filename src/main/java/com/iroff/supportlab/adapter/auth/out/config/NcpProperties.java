package com.iroff.supportlab.adapter.auth.out.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "sms.ncp")
public class NcpProperties {
	private String serviceId;
	private String apiUrl;
	private String accessKey;
	private String secretKey;
	private String countryCode;
	private String from;
}
