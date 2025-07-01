package com.iroff.supportlab.framework.config.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtTokenProvider jwtTokenProvider;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final CustomUserDetailsService customUserDetailsService;
	private final Environment env;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(requests -> requests
				.requestMatchers("/api-docs/**", "/swagger-ui/**", "/swagger-resources/**", "/api/v3/**").permitAll()
				.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.requestMatchers("/h2-console/**").permitAll()
				.requestMatchers("/api/users/sign-up", "/api/users/email", "/api/users/password",
					"/api/users/existence").permitAll()
				.requestMatchers("/api/auth/login", "/api/auth/send-code", "/api/auth/verify-code",
					"/api/auth/send-code-email", "/api/auth/verify-code-email").permitAll()
				.anyRequest().authenticated());

		http
			.csrf(csrf -> csrf.disable())
			.formLogin(form -> form.disable())
			.sessionManagement(sm ->
				sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.httpBasic(httpBasic -> httpBasic.disable());

		http
			.exceptionHandling(exception ->
				exception.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			);

		http
			.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, customUserDetailsService),
				UsernamePasswordAuthenticationFilter.class);

		// todo: h2-console 사용을 위한 코드, 배포시 삭제
		if (Arrays.asList(env.getActiveProfiles()).contains("local")) {
			// H2 콘솔 등에 접근하기 위해 frameOptions 설정을 sameOrigin으로 변경
			http.headers(headersConfigurer -> headersConfigurer
				.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
		}

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
