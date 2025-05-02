package com.iroff.supportlab.domain.user.port.out;

import com.iroff.supportlab.presentation.user.out.persistence.UserEntity;

public interface UserRepository {
	UserEntity findByEmail(String email);
}
