package com.iroff.supportlab.domain.payment.model;

import java.time.OffsetDateTime;

import com.iroff.supportlab.domain.common.model.BaseTime;
import com.iroff.supportlab.domain.payment.model.paymentinfo.*;

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

	public void updatePayment(Payment confirmedPayment) {
		this.version = confirmedPayment.getVersion();
		this.paymentKey = confirmedPayment.getPaymentKey();
		this.type = confirmedPayment.getType();
		this.mid = confirmedPayment.getMid();
		this.currency = confirmedPayment.getCurrency();
		this.method = confirmedPayment.getMethod();
		this.balanceAmount = confirmedPayment.getBalanceAmount();
		this.status = confirmedPayment.getStatus();
		this.requestedAt = confirmedPayment.getRequestedAt();
		this.approvedAt = confirmedPayment.getApprovedAt();
		this.useEscrow = confirmedPayment.getUseEscrow();
		this.lastTransactionKey = confirmedPayment.getLastTransactionKey();
		this.suppliedAmount = confirmedPayment.getSuppliedAmount();
		this.vat = confirmedPayment.getVat();
		this.cultureExpense = confirmedPayment.getCultureExpense();
		this.taxFreeAmount = confirmedPayment.getTaxFreeAmount();
		this.taxExemptionAmount = confirmedPayment.getTaxExemptionAmount();
		this.card = confirmedPayment.getCard();
		this.virtualAccount = confirmedPayment.getVirtualAccount();
		this.mobilePhone = confirmedPayment.getMobilePhone();
		this.giftCertificate = confirmedPayment.getGiftCertificate();
		this.transfer = confirmedPayment.getTransfer();
		this.receipt = confirmedPayment.getReceipt();
		this.easyPay = confirmedPayment.getEasyPay();
		this.failure = confirmedPayment.getFailure();
		this.cashReceipt = confirmedPayment.getCashReceipt();
		this.discount = confirmedPayment.getDiscount();
	}
}