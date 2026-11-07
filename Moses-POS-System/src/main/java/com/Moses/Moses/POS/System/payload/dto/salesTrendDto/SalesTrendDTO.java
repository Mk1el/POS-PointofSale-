package com.Moses.Moses.POS.System.payload.dto.salesTrendDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesTrendDTO {
    private String periodLabel;
    private BigDecimal revenue;
    private Long orderCount;
}