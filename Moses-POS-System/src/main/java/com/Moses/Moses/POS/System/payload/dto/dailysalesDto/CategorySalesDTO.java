package com.Moses.Moses.POS.System.payload.dto.dailysalesDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategorySalesDTO {
    private Long categoryId;
    private String categoryName;
    private Long quantitySold;
    private Double totalSales;
}