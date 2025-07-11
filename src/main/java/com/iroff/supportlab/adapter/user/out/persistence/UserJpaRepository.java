package com.iroff.supportlab.adapter.user.out.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByEmail(String email);

	Optional<UserEntity> findByPhone(String phone);

	boolean existsByEmail(String email);

	boolean existsByPhone(String phone);
}