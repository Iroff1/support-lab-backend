package com.iroff.supportlab.adapter.payment.out.persistence;

import org.springframework.stereotype.Component;

import com.iroff.supportlab.domain.payment.model.Payment;

@Component
public class PaymentMapper {

	public Payment mapToDomain(PaymentEntity entity) {
		if (entity == null) {
			return null;
		}

		return Payment.builder()
			.paymentId(entity.getPaymentId())
			.version(entity.getVersion())
			.paymentKey(entity.getPaymentKey())
			.type(entity.getType())
			.orderId(entity.getOrderId())
			.orderName(entity.getOrderName())
			.mid(entity.getMid())
			.currency(entity.getCurrency())
			.method(entity.getMethod())
			.totalAmount(entity.getTotalAmount())
			.balanceAmount(entity.getBalanceAmount())
			.status(entity.getStatus())
			.requestedAt(entity.getRequestedAt())
			.approvedAt(entity.getApprovedAt())
			.useEscrow(entity.getUseEscrow())
			.lastTransactionKey(entity.getLastTransactionKey())
			.suppliedAmount(entity.getSuppliedAmount())
			.vat(entity.getVat())
			.cultureExpense(entity.getCultureExpense())
			.taxFreeAmount(entity.getTaxFreeAmount())
			.taxExemptionAmount(entity.getTaxExemptionAmount())
			.userId(entity.getUserId()) // userId 매핑 추가
			.card(entity.getCard())
			.virtualAccount(entity.getVirtualAccount())
			.mobilePhone(entity.getMobilePhone())
			.giftCertificate(entity.getGiftCertificate())
			.transfer(entity.getTransfer())
			.receipt(entity.getReceipt())
			.easyPay(entity.getEasyPay())
			.failure(entity.getFailure())
			.cashReceipt(entity.getCashReceipt())
			.discount(entity.getDiscount())
			.createdAt(entity.getCreatedAt())
			.modifiedAt(entity.getModifiedAt())
			.build();
	}

	public PaymentEntity mapToEntity(Payment domain) {
		if (domain == null) {
			return null;
		}

		return PaymentEntity.builder()
			.paymentId(domain.getPaymentId())
			.version(domain.getVersion())
			.paymentKey(domain.getPaymentKey())
			.type(domain.getType())
			.orderId(domain.getOrderId())
			.orderName(domain.getOrderName())
			.mid(domain.getMid())
			.currency(domain.getCurrency())
			.method(domain.getMethod())
			.totalAmount(domain.getTotalAmount())
			.balanceAmount(domain.getBalanceAmount())
			.status(domain.getStatus())
			.requestedAt(domain.getRequestedAt())
			.approvedAt(domain.getApprovedAt())
			.useEscrow(domain.getUseEscrow())
			.lastTransactionKey(domain.getLastTransactionKey())
			.suppliedAmount(domain.getSuppliedAmount())
			.vat(domain.getVat())
			.cultureExpense(domain.getCultureExpense())
			.taxFreeAmount(domain.getTaxFreeAmount())
			.taxExemptionAmount(domain.getTaxExemptionAmount())
			.userId(domain.getUserId()) // userId 매핑 추가
			.card(domain.getCard())
			.virtualAccount(domain.getVirtualAccount())
			.mobilePhone(domain.getMobilePhone())
			.giftCertificate(domain.getGiftCertificate())
			.transfer(domain.getTransfer())
			.receipt(domain.getReceipt())
			.easyPay(domain.getEasyPay())
			.failure(domain.getFailure())
			.cashReceipt(domain.getCashReceipt())
			.discount(domain.getDiscount())
			.build();
	}
}
