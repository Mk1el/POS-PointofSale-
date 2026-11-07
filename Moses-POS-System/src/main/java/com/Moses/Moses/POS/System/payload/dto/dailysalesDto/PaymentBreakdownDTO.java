package com.Moses.Moses.POS.System.payload.dto.dailysalesDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentBreakdownDTO {
    private String paymentMethod;
    private Long totalOrders;
    private BigDecimal totalAmount;
    private Long totalTransactions;
    private Long successfulTransactions;
    private Long failedTransactions;
}
