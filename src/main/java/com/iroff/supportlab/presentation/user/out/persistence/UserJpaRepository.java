package com.iroff.supportlab.presentation.user.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, String> {
	UserEntity findByEmail(String email);
}
