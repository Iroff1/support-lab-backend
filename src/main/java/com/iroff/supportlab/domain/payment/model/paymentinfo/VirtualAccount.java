package com.iroff.supportlab.domain.payment.model.paymentinfo;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class VirtualAccount {
    private String accountNumber;
    private String bankCode;
	private String customerName;
	private OffsetDateTime dueDate;
	private String refundStatus;
    private Boolean expired;
    private String settlementStatus;
}
