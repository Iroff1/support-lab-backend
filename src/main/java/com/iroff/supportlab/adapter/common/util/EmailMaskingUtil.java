package com.iroff.supportlab.adapter.common.util;

public class EmailMaskingUtil {
	private EmailMaskingUtil() {
	}

	public static String maskMiddle(String email) {
		if (email == null || !email.contains("@")) {
			return email;
		}
		String[] parts = email.split("@", 2);
		String local = parts[0];
		String domain = parts[1];

		String maskedLocal;
		int length = local.length();
		if (length <= 4) {
			maskedLocal = local.substring(0, length / 2) + "*".repeat(length - length / 2);
		} else {
			maskedLocal = local.substring(0, 2) + "*".repeat(length - 4) + local.substring(length - 2);
		}
		return maskedLocal + "@" + domain;
	}
}
