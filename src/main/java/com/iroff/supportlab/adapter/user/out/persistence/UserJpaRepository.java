package com.iroff.supportlab.adapter.user.out.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.iroff.supportlab.domain.user.model.User;

import jakarta.persistence.LockModeType;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
	Optional<User> findByEmail(String email);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	void save(User user);

	boolean existsByEmail(String email);

	boolean existsByPhone(String phone);
}
