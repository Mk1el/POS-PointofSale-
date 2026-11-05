package com.Moses.Moses.POS.System.payload.dto.dailysalesDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentBreakdownDTO {
    private String paymentMethod;
    private Long totalOrders;
    private Double totalAmount;
}
