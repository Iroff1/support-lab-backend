package com.iroff.supportlab.domain.payment.model;

import java.time.OffsetDateTime;

import com.iroff.supportlab.domain.common.model.BaseTime;
import com.iroff.supportlab.domain.payment.model.paymentinfo.Card;
import com.iroff.supportlab.domain.payment.model.paymentinfo.CashReceipt;
import com.iroff.supportlab.domain.payment.model.paymentinfo.Discount;
import com.iroff.supportlab.domain.payment.model.paymentinfo.EasyPay;
import com.iroff.supportlab.domain.payment.model.paymentinfo.Failure;
import com.iroff.supportlab.domain.payment.model.paymentinfo.GiftCertificate;
import com.iroff.supportlab.domain.payment.model.paymentinfo.MobilePhone;
import com.iroff.supportlab.domain.payment.model.paymentinfo.Receipt;
import com.iroff.supportlab.domain.payment.model.paymentinfo.Transfer;
import com.iroff.supportlab.domain.payment.model.paymentinfo.VirtualAccount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends BaseTime {
	private Long paymentId;
	private String version;
	private String paymentKey;
	private String type;
	private String orderId;
	private String orderName;
	private String mid;
	private String currency;
	private PaymentMethod method;
	private Integer totalAmount;
	private Integer balanceAmount;
	private PaymentStatus status;
	private OffsetDateTime requestedAt;
	private OffsetDateTime approvedAt;
	private Boolean useEscrow;
	private String lastTransactionKey;
	private Integer suppliedAmount;
	private Integer vat;
	private Boolean cultureExpense;
	private Integer taxFreeAmount;
	private Integer taxExemptionAmount;
	private Long userId; // userId 필드 추가

	private Card card;
	private VirtualAccount virtualAccount;
	private MobilePhone mobilePhone;
	private GiftCertificate giftCertificate;
	private Transfer transfer;
	private Receipt receipt;
	private EasyPay easyPay;
	private Failure failure;
	private CashReceipt cashReceipt;
	private Discount discount;
}