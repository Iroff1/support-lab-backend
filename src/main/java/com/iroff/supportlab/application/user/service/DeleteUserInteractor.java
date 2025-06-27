package com.iroff.supportlab.application.user.service;

import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iroff.supportlab.application.user.dto.DeleteUserRequest;
import com.iroff.supportlab.domain.auth.port.in.exception.AuthError;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.common.port.in.exception.ErrorInfo;
import com.iroff.supportlab.domain.user.model.User;
import com.iroff.supportlab.domain.user.port.in.DeleteUserUseCase;
import com.iroff.supportlab.domain.user.port.in.exception.UserError;
import com.iroff.supportlab.domain.user.port.out.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteUserInteractor implements DeleteUserUseCase {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	@Override
	public void deleteUser(Long userId, DeleteUserRequest request) {
		String password = request.password();
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new DomainException(UserError.USER_NOT_FOUND));
		checkCondition(passwordEncoder.matches(password, user.getPassword()), AuthError.INVALID_PASSWORD);
		try {
			if (userRepository.existsById(userId)) {
				userRepository.deleteById(userId);
			} else {
				throw new DomainException(UserError.USER_NOT_FOUND);
			}
		} catch (DataAccessException e) {
			throw new DomainException(UserError.DELETE_USER_FAILED);
		}
	}

	void checkCondition(boolean condition, ErrorInfo error) {
		if (!condition) {
			throw new DomainException(error);
		}
	}
}
