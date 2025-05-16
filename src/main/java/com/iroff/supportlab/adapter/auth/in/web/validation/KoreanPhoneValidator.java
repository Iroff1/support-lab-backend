package com.iroff.supportlab.adapter.auth.in.web.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class KoreanPhoneValidator implements ConstraintValidator<KoreanPhone, String> {

	// 010,011,016~019 + 3~4 + 4 자리
	private static final String REGEX = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$";

	@Override
	public void initialize(KoreanPhone annotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		return value.matches(REGEX);
	}
}