package com.iroff.supportlab.domain.user.port.out;

import java.util.Optional;

import com.iroff.supportlab.domain.user.model.User;

public interface UserRepository {
	Optional<User> findByEmail(String email);

	void save(User user);

	boolean existsByEmail(String email);
}
