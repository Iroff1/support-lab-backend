package com.iroff.supportlab.application.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.iroff.supportlab.application.user.dto.ChangePasswordRequest;
import com.iroff.supportlab.application.user.dto.ChangePasswordResponse;
import com.iroff.supportlab.application.user.dto.vo.SendChangePasswordEmailResult;
import com.iroff.supportlab.domain.auth.model.vo.VerificationType;
import com.iroff.supportlab.domain.auth.port.in.exception.AuthError;
import com.iroff.supportlab.domain.auth.port.out.VerificationStateRepository;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.common.port.in.exception.ErrorInfo;
import com.iroff.supportlab.domain.email.port.in.exception.EmailError;
import com.iroff.supportlab.domain.email.port.out.SendEmailPort;
import com.iroff.supportlab.domain.user.model.User;
import com.iroff.supportlab.domain.user.port.in.ChangePasswordUseCase;
import com.iroff.supportlab.domain.user.port.out.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChangePasswordInteractor implements ChangePasswordUseCase {

	private static final String SUBJECT = "테스트용 이메일 제목입니다.";
	private static final String CONTENT = "테스트용 이메일입니다.";
	private final SendEmailPort sendEmailPort;
	private final UserRepository userRepository;
	private final VerificationStateRepository verificationStateRepository;

	@Override
	public ChangePasswordResponse changePassword(ChangePasswordRequest request) {
		String email = request.email();
		String name = request.name();
		String phone = request.phone();
		Optional<User> user = userRepository.findByPhone(phone);

		checkCondition(verificationStateRepository.isVerified(VerificationType.FIND_PASSWORD_VERIFIED, phone),
			AuthError.VERIFY_CODE_FAILED);
		verificationStateRepository.remove(VerificationType.FIND_PASSWORD_VERIFIED, phone);

		log.info("email: {}, name: {}, phone: {}", email, name, phone);

		try {
			if (user.isPresent() && user.get().getName().equals(name) && user.get().getEmail().equals(email)) {
				sendEmailPort.sendEmail(email, SUBJECT, CONTENT);
			}
		} catch (Exception e) {
			throw new DomainException(EmailError.SEND_EMAIL_FAILED);
		}

		return new ChangePasswordResponse(SendChangePasswordEmailResult.SUCCESS.getMessage());
	}

	private void checkCondition(boolean condition, ErrorInfo error) {
		if (!condition) {
			throw new DomainException(error);
		}
	}
}
