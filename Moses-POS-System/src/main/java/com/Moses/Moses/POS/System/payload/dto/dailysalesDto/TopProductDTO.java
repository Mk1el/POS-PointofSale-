package com.Moses.Moses.POS.System.payload.dto.dailysalesDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopProductDTO {
    private Long productId;
    private String productName;
    private Long quantitySold;
    private Double totalRevenue;
    private Double percentageContribution;
}