package com.iroff.supportlab.adapter.config.global.security;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private static final String[] WHITELIST = {
		"/api/auth/login",
		"/api/users/sign-up",
		"/swagger-ui",
		"/v3/api-docs"
	};
	private final JwtTokenProvider jwtTokenProvider;
	private final CustomUserDetailsService customUserDetailsService;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getRequestURI();
		return Arrays.stream(WHITELIST).anyMatch(path::startsWith);
	}

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {

		// 1. Authorization 헤더에서 Bearer 토큰 추출
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);

			// 2. 유효성 검사
			if (jwtTokenProvider.validateToken(token)) {
				// 3. 사용자 ID 추출
				Long userId = jwtTokenProvider.getUserId(token);
				CustomUserDetails userDetails = customUserDetailsService.loadUserById(userId);

				// 4. 인증 객체 생성 및 SecurityContext에 등록
				UsernamePasswordAuthenticationToken authentication =
					new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		// 5. 다음 필터로 요청 전달
		filterChain.doFilter(request, response);
	}
}
