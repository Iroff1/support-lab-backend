package com.iroff.supportlab.framework.config.web;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final WebProperties webProperties;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		List<String> hosts = webProperties.getHosts();
		registry.addMapping("/api/**")
			.allowedOrigins(hosts.toArray(new String[0]))
			.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
			.allowedHeaders("*")
			.allowCredentials(true); // 쿠키 필요 시
	}
}