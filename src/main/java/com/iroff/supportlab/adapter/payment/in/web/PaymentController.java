package com.iroff.supportlab.adapter.payment.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iroff.supportlab.adapter.common.in.web.exception.APIException;
import com.iroff.supportlab.adapter.common.in.web.exception.ErrorStatus;
import com.iroff.supportlab.adapter.common.in.web.exception.ErrorStatusResolver;
import com.iroff.supportlab.application.payment.dto.ConfirmPaymentRequest;
import com.iroff.supportlab.application.payment.dto.RequestPaymentRequest;
import com.iroff.supportlab.application.payment.dto.RequestPaymentResponse;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;
import com.iroff.supportlab.domain.payment.model.Payment;
import com.iroff.supportlab.domain.payment.model.RequestedPayment;
import com.iroff.supportlab.domain.payment.port.in.ConfirmPaymentUseCase;
import com.iroff.supportlab.domain.payment.port.in.RequestPaymentUseCase;
import com.iroff.supportlab.framework.config.security.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Tag(name = "결제", description = "결제 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

	private final RequestPaymentUseCase requestPaymentUseCase;
	private final ConfirmPaymentUseCase confirmPaymentUseCase;
	private final ErrorStatusResolver errorStatusResolver;

	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "결제 요청", description = "결제를 요청하고 임시 결제 정보를 저장합니다.")
	@PostMapping("/request")
	public ResponseEntity<RequestPaymentResponse> requestPayment(
		@AuthenticationPrincipal CustomUserDetails user,
		@RequestBody RequestPaymentRequest request
	) {
		try {
			// Long userId = user.getUser().getId();
			Long userId = 2L;
			RequestedPayment requestedPayment = requestPaymentUseCase.requestPayment(userId, request);
			return ResponseEntity.ok(RequestPaymentResponse.from(requestedPayment));
		} catch (DomainException e) {
			ErrorStatus errorStatus = errorStatusResolver.resolve(e.getError());
			throw new APIException(e, errorStatus);
		}
	}

	@SecurityRequirement(name = "bearerAuth") // 로그인된 사용자만 접근 가능하도록 설정
	@Operation(summary = "결제 승인", description = "결제를 승인하고 최종 결제 정보를 저장합니다.")
	@PostMapping("/confirm")
	public Mono<ResponseEntity<Payment>> confirmPayment(
		@AuthenticationPrincipal CustomUserDetails user, // 사용자 정보 주입
		@RequestBody ConfirmPaymentRequest request
	) {
		try {
			// Long userId = user.getUser().getId();
			Long userId = 2L;
			Mono<Payment> monoResponse = confirmPaymentUseCase.confirmPayment(userId, request);
			return monoResponse.map(ResponseEntity::ok);
		} catch (DomainException e) {
			ErrorStatus errorStatus = errorStatusResolver.resolve(e.getError());
			throw new APIException(e, errorStatus);
		}
	}
}
