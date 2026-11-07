package com.Moses.Moses.POS.System.payload.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodSalesDTO {
    private String paymentMethod;
    private BigDecimal totalAmount;
    private Long transactionCount;
}