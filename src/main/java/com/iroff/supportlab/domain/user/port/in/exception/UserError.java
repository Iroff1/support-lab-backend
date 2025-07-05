package com.iroff.supportlab.domain.user.port.in.exception;

import com.iroff.supportlab.domain.common.port.in.exception.ErrorInfo;

public enum UserError implements ErrorInfo {
	EMAIL_ALREADY_EXISTS("이미 사용 중인 이메일입니다."),
	PHONE_ALREADY_EXISTS("이미 사용 중인 전화번호입니다."),
	INVALID_NAME("유효하지 않은 이름입니다."),
	INVALID_EMAIL("유효하지 않은 이메일 형식입니다."),
	INVALID_PASSWORD_LENGTH("비밀번호는 8자 이상이어야 합니다."),
	INVALID_PASSWORD_UPPERCASE("비밀번호는 최소 하나 이상의 대문자를 포함해야 합니다."),
	INVALID_PASSWORD_LOWERCASE("비밀번호는 최소 하나 이상의 소문자를 포함해야 합니다."),
	INVALID_PASSWORD_NUMBER("비밀번호는 최소 하나 이상의 숫자를 포함해야 합니다."),
	INVALID_PASSWORD_SPECIAL("비밀번호는 최소 하나 이상의 특수문자를 포함해야 합니다."),
	INVALID_PASSWORD_SEQUENCE("연속된 문자나 반복된 문자는 사용할 수 없습니다."),
	VERIFICATION_FAILED("휴대폰 인증을 먼저 진행해주세요."),
	TERMS_OF_SERVICE_AGREE_IS_NECCESSARY("이용 약관에 동의해야 합니다."),
	PRIVACY_POLICY_AGREE_IS_NECCESARY("개인정보 수집 및 이용에 동의해야 합니다."),
	INVALID_MARKETING_AGREE("마케팅 수신 동의 여부는 필수입니다."),
	USER_NOT_FOUND("존재하지 않는 사용자입니다."),
	PASSWORD_ALREADY_CHANGED("다른 곳에서 비밀번호가 바뀌어 요청이 취소되었습니다."),
	DELETE_USER_FAILED("이미 삭제된 사용자이거나 과정에 문제가 발생했습니다."),
	UPDATE_PASSWORD_FAILED("비밀번호 변경에 실패했습니다."),
	WRONG_PASSWORD("비밀번호가 맞지 않습니다."),
	SAME_NAME_NOT_ALLOWED("이전과 다른 이름을 사용해주세요."),
	SAME_PASSWORD_NOT_ALLOWED("이전과 다른 비밀번호를 사용해주세요.");

	private final String desc;

	UserError(String desc) {
		this.desc = desc;
	}

	@Override
	public String getCode() {
		return String.format("U%03d", ordinal() + 1);
	}

	@Override
	public String getDesc() {
		return desc;
	}
}
