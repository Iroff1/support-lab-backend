package com.iroff.supportlab.adapter.common.in.web.dto.vo;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResponseCode {
	OK("200", "OK", "요청에 성공했습니다."),
	CREATED("201", "CREATED", "리소스가 성공적으로 생성되었습니다."),
	NO_CONTENT("204", "NO_CONTENT", "요청이 성공적으로 처리되었으나 반환할 내용이 없습니다."),

	MULTIPLE_CHOICES("300", "MULTIPLE_CHOICES", "요청한 리소스에 대해 여러 선택지가 있습니다."),
	MOVED_PERMANENTLY("301", "MOVED_PERMANENTLY", "요청한 리소스가 새로운 URL로 영구 이동되었습니다."),
	FOUND("302", "FOUND", "요청한 리소스가 일시적으로 다른 URL에 위치합니다."),
	SEE_OTHER("303", "SEE_OTHER", "다른 URL에서 요청한 리소스를 찾을 수 있습니다."),
	NOT_MODIFIED("304", "NOT_MODIFIED", "요청한 리소스가 마지막 요청 이후 변경되지 않았습니다."),
	TEMPORARY_REDIRECT("307", "TEMPORARY_REDIRECT", "요청한 리소스가 임시적으로 다른 URL에 있습니다."),
	PERMANENT_REDIRECT("308", "PERMANENT_REDIRECT", "요청한 리소스가 영구적으로 다른 URL로 이동되었습니다."),

	BAD_REQUEST("400", "BAD_REQUEST", "잘못된 요청입니다."),
	UNAUTHORIZED("401", "UNAUTHORIZED", "인증이 필요합니다."),
	FORBIDDEN("403", "FORBIDDEN", "접근 권한이 없습니다."),
	NOT_FOUND("404", "NOT_FOUND", "리소스를 찾을 수 없습니다."),
	METHOD_NOT_ALLOWED("405", "METHOD_NOT_ALLOWED", "허용되지 않은 HTTP 메서드입니다."),
	CONFLICT("409", "CONFLICT", "리소스 충돌이 발생했습니다."),

	INTERNAL_SERVER_ERROR("500", "INTERNAL_SERVER_ERROR", "서버 내부 오류가 발생했습니다."),
	SERVICE_UNAVAILABLE("503", "SERVICE_UNAVAILABLE", "서비스를 사용할 수 없습니다.");

	private final String code;
	private final String status;
	private final String message;

	ResponseCode(String code, String status, String message) {
		this.code = code;
		this.status = status;
		this.message = message;
	}

	public static ResponseCode getResponseCode(HttpStatus httpStatus) {
		for (ResponseCode code : ResponseCode.values()) {
			if (code.getStatus().equals(httpStatus.name())) {
				return code;
			}
		}
		return ResponseCode.BAD_REQUEST;
	}

	public String getCode() {
		return code;
	}

	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}
}
