package com.iroff.supportlab.adapter.config.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(requests -> requests
				.requestMatchers("/api-docs/**", "/swagger-ui/**", "/swagger-resources/**", "/api/v3/**").permitAll()
				.requestMatchers("/h2-console/**").permitAll()
				.requestMatchers("/api/users/sign-up").permitAll()
				.requestMatchers("/api/auth/send-code").permitAll()
				.requestMatchers("/api/auth/verify-code").permitAll()
				.anyRequest().authenticated());

		http
			.csrf(csrf -> csrf.disable())
			.formLogin(form -> form.disable())
			.httpBasic(httpBasic -> httpBasic.disable());

		// todo: h2-consoel 사용을 위한 코드, 배포시 삭제
		http.headers(headersConfigurer -> headersConfigurer
			.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
