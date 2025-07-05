package com.iroff.supportlab.domain.user.util;

public class NameValidator {
	// 한글 2~6자만 허용
	private static final String NAME_PATTERN = "^[가-힣]{2,6}$";

	public static boolean isValidName(String name) {
		return name != null && name.matches(NAME_PATTERN);
	}
}