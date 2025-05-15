package com.iroff.supportlab.adapter.auth.out.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.iroff.supportlab.adapter.auth.in.web.validation.KoreanPhone;
import com.iroff.supportlab.adapter.auth.out.dto.vo.NcpSmsRequestContentType;
import com.iroff.supportlab.adapter.auth.out.dto.vo.NcpSmsRequestCountryCode;
import com.iroff.supportlab.adapter.auth.out.dto.vo.NcpSmsRequestType;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NcpSmsRequest {

	/**
	 * SMS Type (SMS | LMS | MMS), 소문자 가능
	 */
	@Builder.Default
	@NotNull
	private NcpSmsRequestType type = NcpSmsRequestType.SMS;

	/**
	 * 메시지 Type (COMM: 일반메시지, AD: 광고메시지)
	 * default: COMM
	 */
	@Builder.Default
	@NotNull
	private NcpSmsRequestContentType contentType = NcpSmsRequestContentType.COMM;

	/**
	 * 국가 번호 (기본: 82)
	 */
	@Builder.Default
	@NotNull
	private NcpSmsRequestCountryCode countryCode = NcpSmsRequestCountryCode.KOREA;

	/**
	 * 발신번호 (사전 등록된 발신번호만 사용 가능)
	 */
	@NotBlank
	private String from;

	/**
	 * 기본 메시지 제목 (LMS, MMS에서만 사용 가능)
	 * 최대 40byte
	 */
	@Size(max = 40, message = "메시지 제목의 최대 길이는 40 bytes 입니다.")
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
	@Size(min = 1, max = 100, message = "메시지는 1개부터 100개까지만 보낼 수 있습니다.")
	private List<@Valid Message> messages;

	/**
	 * 첨부 파일 목록 (MMS에서만 사용 가능)
	 */
	private List<@Valid FileDto> files;

	/**
	 * 예약 발송 일시 (yyyy-MM-dd HH:mm)
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime reserveTime;

	/**
	 * 예약 일시 타임존 (기본: Asia/Seoul)
	 * TZ database name
	 */
	private String reserveTimeZone;

	@Getter
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
		@Size(max = 40, message = "메시지 제목의 최대 길이는 40 bytes 입니다.")
		private String subject;

		/**
		 * 개별 메시지 내용
		 * SMS: 최대 90byte, LMS/MMS: 최대 2000byte
		 */
		private String content;
	}

	@Getter
	@Builder
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
