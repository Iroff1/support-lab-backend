package com.iroff.supportlab.presentation.user.out.persistence;

import org.springframework.stereotype.Repository;

import com.iroff.supportlab.domain.user.port.out.UserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

	private final UserJpaRepository userJpaRepository;

	@Override
	public UserEntity findByEmail(String email) {
		return userJpaRepository.findByEmail(email);
	}
}
