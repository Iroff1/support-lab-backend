package com.iroff.supportlab.presentation.user.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, String> {
	Optional<UserEntity> findByEmail(String email);
}
