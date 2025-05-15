package com.iroff.supportlab.adapter.auth.out.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NcpSmsRequest {

	/**
	 * SMS Type (SMS | LMS | MMS), 소문자 가능
	 */
	@Builder.Default
	@NotBlank
	@Pattern(regexp = "(?i)^(SMS|LMS|MMS)$", message = "type must be SMS, LMS, or MMS")
	private String type = "SMS";

	/**
	 * 메시지 Type (COMM: 일반메시지, AD: 광고메시지)
	 * default: COMM
	 */
	@Builder.Default
	@Pattern(regexp = "^(COMM|AD)$", message = "contentType must be COMM or AD")
	private String contentType = "COMM";

	/**
	 * 국가 번호 (기본: 82)
	 */
	@Builder.Default
	@Pattern(regexp = "\\d+", message = "countryCode must be numeric")
	private String countryCode = "82";

	/**
	 * 발신번호 (사전 등록된 발신번호만 사용 가능)
	 */
	@NotBlank
	private String from;

	/**
	 * 기본 메시지 제목 (LMS, MMS에서만 사용 가능)
	 * 최대 40byte
	 */
	@Size(max = 40, message = "subject max length is 40 bytes")
	private String subject;

	/**
	 * 기본 메시지 내용
	 * SMS: 최대 90byte, LMS/MMS: 최대 2000byte
	 */
	@NotBlank
	private String content;

	/**
	 * 메시지 정보 (최대 100개)
	 */
	@NotNull
	@Size(min = 1, max = 100, message = "messages size must be between 1 and 100")
	private List<@Valid Message> messages;

	/**
	 * 첨부 파일 목록 (MMS에서만 사용 가능)
	 */
	private List<@Valid FileDto> files;

	/**
	 * 예약 발송 일시 (yyyy-MM-dd HH:mm)
	 */
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$", message = "reserveTime format must be yyyy-MM-dd HH:mm")
	private String reserveTime;

	/**
	 * 예약 일시 타임존 (기본: Asia/Seoul)
	 * TZ database name
	 */
	private String reserveTimeZone = "Asia/Seoul";

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class Message {
		/**
		 * 수신번호 (숫자만 입력 가능, '-' 제외)
		 */
		@NotBlank
		@KoreanPhone
		private String to;

		/**
		 * 개별 메시지 제목 (LMS, MMS에서만 사용 가능)
		 * 최대 40byte
		 */
		@Size(max = 40, message = "message subject max length is 40 bytes")
		private String subject;

		/**
		 * 개별 메시지 내용
		 * SMS: 최대 90byte, LMS/MMS: 최대 2000byte
		 */
		private String content;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class FileDto {
		/**
		 * 파일 아이디 (MMS에서만 사용 가능)
		 */
		@NotBlank
		private String fileId;
	}
}
