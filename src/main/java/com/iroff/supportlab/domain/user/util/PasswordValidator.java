package com.iroff.supportlab.domain.user.util;

import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.user.port.in.exception.UserError;

public class PasswordValidator {
	public static void validatePassword(String password) {
		if (password == null || password.length() < 8) {
			throw new DomainException(UserError.INVALID_PASSWORD_LENGTH);
		}

		if (!password.matches(".*[A-Z].*")) {
			throw new DomainException(UserError.INVALID_PASSWORD_UPPERCASE);
		}

		if (!password.matches(".*[a-z].*")) {
			throw new DomainException(UserError.INVALID_PASSWORD_LOWERCASE);
		}

		if (!password.matches(".*[0-9].*")) {
			throw new DomainException(UserError.INVALID_PASSWORD_NUMBER);
		}

		if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
			throw new DomainException(UserError.INVALID_PASSWORD_SPECIAL);
		}

		// 연속된 문자나 반복된 문자 검사
		for (int i = 0; i < password.length() - 2; i++) {
			char c1 = password.charAt(i);
			char c2 = password.charAt(i + 1);
			char c3 = password.charAt(i + 2);

			// 연속된 숫자 검사 (예: 123, 234)
			if (Character.isDigit(c1) && Character.isDigit(c2) && Character.isDigit(c3)) {
				if (c2 == c1 + 1 && c3 == c2 + 1) {
					throw new DomainException(UserError.INVALID_PASSWORD_SEQUENCE);
				}
			}

			// 연속된 문자 검사 (예: abc, bcd)
			if (Character.isLetter(c1) && Character.isLetter(c2) && Character.isLetter(c3)) {
				if (c2 == c1 + 1 && c3 == c2 + 1) {
					throw new DomainException(UserError.INVALID_PASSWORD_SEQUENCE);
				}
			}

			// 반복된 문자 검사 (예: aaa, 111)
			if (c1 == c2 && c2 == c3) {
				throw new DomainException(UserError.INVALID_PASSWORD_SEQUENCE);
			}
		}
	}
}
