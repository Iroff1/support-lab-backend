package com.iroff.supportlab.presentation.user.out.persistence;

import com.iroff.supportlab.domain.user.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

	private final UserJpaRepository userJpaRepository;

	@Override
	public Optional<UserEntity> findByEmail(String email) {
		return userJpaRepository.findByEmail(email);
	}
}
