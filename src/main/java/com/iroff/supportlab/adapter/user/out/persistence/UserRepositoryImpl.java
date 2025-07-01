package com.iroff.supportlab.adapter.user.out.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.iroff.supportlab.domain.user.model.User;
import com.iroff.supportlab.domain.user.port.out.UserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

	private final UserJpaRepository userJpaRepository;

	@Override
	public Optional<User> findById(Long id) {
		return userJpaRepository.findById(id).map(userEntity -> userEntity);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userJpaRepository.findByEmail(email);
	}

	@Override
	public Optional<User> findByPhone(String phone) {
		return userJpaRepository.findByPhone(phone);
	}

	@Override
	public void deleteById(Long id) {
		userJpaRepository.deleteById(id);
	}

	@Override
	public void save(User user) {
		userJpaRepository.save(user);
	}

	@Override
	public boolean existsById(Long id) {
		return userJpaRepository.existsById(id);
	}

	@Override
	public boolean existsByEmail(String email) {
		return userJpaRepository.existsByEmail(email);
	}

	@Override
	public boolean existsByPhone(String phone) {
		return userJpaRepository.existsByPhone(phone);
	}

	@Override
	public long count() {
		return userJpaRepository.count();
	}
}
