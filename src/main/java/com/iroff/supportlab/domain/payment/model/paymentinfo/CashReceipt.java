package com.iroff.supportlab.domain.payment.model.paymentinfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CashReceipt {
    private String type;
    private String receiptKey;
    private String issueNumber;
    private String receiptUrl;
    private Integer amount;
    private Integer taxFreeAmount;
}
