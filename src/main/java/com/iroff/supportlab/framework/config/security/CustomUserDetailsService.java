package com.iroff.supportlab.framework.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.iroff.supportlab.adapter.user.out.persistence.UserJpaRepository;
import com.iroff.supportlab.domain.user.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserJpaRepository userJpaRepository;

	// todo: 사용자 지정 Exception 추가 시 수정 필요
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userJpaRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("해당 사용자가 없습니다."));
		return new CustomUserDetails(user);
	}

	public CustomUserDetails loadUserById(Long userId) {
		User user = userJpaRepository.findById(userId)
			.orElseThrow(() -> new UsernameNotFoundException("해당 사용자가 없습니다."));
		return new CustomUserDetails(user);
	}
}
