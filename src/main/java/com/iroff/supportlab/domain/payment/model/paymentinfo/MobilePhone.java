package com.iroff.supportlab.domain.payment.model.paymentinfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MobilePhone {
    private String customerMobilePhone;
    private String settlementStatus;
    private String receiptUrl;
}
