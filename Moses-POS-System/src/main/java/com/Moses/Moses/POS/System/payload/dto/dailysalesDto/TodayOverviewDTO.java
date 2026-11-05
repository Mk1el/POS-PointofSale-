package com.Moses.Moses.POS.System.payload.dto.dailysalesDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodayOverviewDTO {
    private Long totalOrders;
    private Double totalSales;
    private Long uniqueCustomers;
    private Long totalItemsSold;
    private Double averageOrderValue;
}