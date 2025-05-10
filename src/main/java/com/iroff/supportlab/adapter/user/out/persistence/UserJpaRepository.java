package com.iroff.supportlab.adapter.user.out.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iroff.supportlab.domain.user.model.User;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

	void save(User user);
}
