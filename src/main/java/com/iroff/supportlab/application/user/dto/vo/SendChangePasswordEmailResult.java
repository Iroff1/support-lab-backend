package com.iroff.supportlab.application.user.dto.vo;

import lombok.Getter;

@Getter
public enum SendChangePasswordEmailResult {
	SUCCESS("가입하신 이메일로 비밀번호 재설정 메일을 전송했습니다. 메일함을 확인해주세요. 만약 메일이 오지 않았다면 입력하신 정보가 잘못되었거나 가입하지 않은 사용자일 수 있습니다."),
	FAIL("이메일 발송에 실패했습니다.");

	private String message;

	SendChangePasswordEmailResult(String message) {
		this.message = message;
	}
}
