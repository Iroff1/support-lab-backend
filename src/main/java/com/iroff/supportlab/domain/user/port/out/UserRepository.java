package com.iroff.supportlab.domain.user.port.out;

import com.iroff.supportlab.presentation.user.out.persistence.UserEntity;

import java.util.Optional;

public interface UserRepository {
	Optional<UserEntity> findByEmail(String email);
}
