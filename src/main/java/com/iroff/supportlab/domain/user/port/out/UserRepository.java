package com.iroff.supportlab.domain.user.port.out;

import java.util.Optional;

import com.iroff.supportlab.domain.user.model.User;

public interface UserRepository {
	Optional<User> findById(Long id);

	Optional<User> findByEmail(String email);

	Optional<User> findByPhone(String phone);

	void deleteById(Long id);

	void save(User user);

	boolean existsById(Long id);

	boolean existsByEmail(String email);

	boolean existsByPhone(String phone);

	long count();
}
