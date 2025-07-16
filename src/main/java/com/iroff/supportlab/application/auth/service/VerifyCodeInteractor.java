package com.iroff.supportlab.application.auth.service;

import java.time.Duration;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import com.iroff.supportlab.application.auth.dto.VerifyCodeRequest;
import com.iroff.supportlab.application.auth.dto.VerifyCodeResponse;
import com.iroff.supportlab.application.auth.dto.vo.VerifyCodeResponseStatus;
import com.iroff.supportlab.domain.auth.model.vo.VerificationType;
import com.iroff.supportlab.domain.auth.port.in.VerifyCodeUseCase;
import com.iroff.supportlab.domain.auth.port.in.exception.AuthError;
import com.iroff.supportlab.domain.auth.port.out.VerificationCodeRepository;
import com.iroff.supportlab.domain.auth.port.out.VerificationStateRepository;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerifyCodeInteractor implements VerifyCodeUseCase {

	private final VerificationCodeRepository codeRepository;
	private final VerificationStateRepository stateRepository;

	@Override
	public VerifyCodeResponse verifyCode(VerifyCodeRequest request, Long userId) {
		VerificationType type = request.type();
		String phone = request.phone();
		String code = request.code();

		Supplier<Optional<String>> findStrategy;
		BiConsumer<VerificationType, String> removeStrategy;

		if (userId == null) {
			findStrategy = () -> codeRepository.find(type, phone);
			removeStrategy = codeRepository::remove;
		} else {
			findStrategy = () -> codeRepository.findByUser(type, phone, userId);
			removeStrategy = (t, p) -> codeRepository.removeByUser(t, p, userId);
		}

		String savedCode = findStrategy.get()
			.orElseThrow(() -> new DomainException(AuthError.CODE_NOT_EXISTS));

		if (!savedCode.equals(code)) {
			throw new DomainException(AuthError.VERIFY_CODE_FAILED);
		}
		removeStrategy.accept(type, phone);

		VerificationType verifiedType = switch (type) {
			case SIGN_UP_CODE -> VerificationType.SIGN_UP_VERIFIED;
			case FIND_EMAIL_CODE -> VerificationType.FIND_EMAIL_VERIFIED;
			case FIND_PASSWORD_CODE -> VerificationType.FIND_PASSWORD_VERIFIED;
			case UPDATE_PHONE_CODE -> VerificationType.UPDATE_PHONE_VERIFIED;
			default -> throw new DomainException(AuthError.INVALID_REQUEST);
		};
		if (userId == null && type != VerificationType.UPDATE_PHONE_CODE) {
			stateRepository.markedVerified(verifiedType, phone, Duration.ofMinutes(10));
		} else if (userId != null && type == VerificationType.UPDATE_PHONE_CODE) {
			stateRepository.markedVerifiedByUser(verifiedType, phone, userId, Duration.ofMinutes(10));
		} else {
			throw new DomainException(AuthError.INVALID_REQUEST);
		}
		return new VerifyCodeResponse(VerifyCodeResponseStatus.SUCCESS);
	}

	@Override
	public boolean validate(VerifyCodeRequest param) {
		return VerifyCodeUseCase.super.validate(param);
	}
}