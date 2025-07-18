package com.iroff.supportlab.adapter.payment.out.persistence;

import java.time.OffsetDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.iroff.supportlab.adapter.common.out.persistence.BaseTimeEntity;
import com.iroff.supportlab.domain.payment.model.PaymentMethod;
import com.iroff.supportlab.domain.payment.model.PaymentStatus;
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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "payments")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_id")
	private Long paymentId;

	private String version;

	@Column(unique = true)
	private String paymentKey;

	private String type;

	@Column(nullable = false, unique = true)
	private String orderId;

	@Column(nullable = false)
	private String orderName;

	private String mid;

	private String currency;

	@Enumerated(EnumType.STRING)
	private PaymentMethod method;

	@Column(nullable = false)
	private Integer totalAmount;

	private Integer balanceAmount;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
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

	private Long userId;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(columnDefinition = "json")
	private Card card;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(columnDefinition = "json")
	private VirtualAccount virtualAccount;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(columnDefinition = "json")
	private MobilePhone mobilePhone;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(columnDefinition = "json")
	private GiftCertificate giftCertificate;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(columnDefinition = "json")
	private Transfer transfer;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(columnDefinition = "json")
	private Receipt receipt;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(columnDefinition = "json")
	private EasyPay easyPay;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(columnDefinition = "json")
	private Failure failure;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(columnDefinition = "json")
	private CashReceipt cashReceipt;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(columnDefinition = "json")
	private Discount discount;
}