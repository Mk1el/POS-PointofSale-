package com.Moses.Moses.POS.System.payload.dto.dailysalesDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopCashierDTO {
    private Long cashierId;
    private String cashierName;
    private Long totalOrders;
    private Double totalRevenue;
}