package com.iroff.supportlab.domain.payment.model;

import com.iroff.supportlab.domain.common.model.BaseTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
public class RequestedPayment extends BaseTime {
	private Long requestedPaymentId;
	private String orderId;
	private String orderName;
	private Integer amount;
	private Long userId;
}
