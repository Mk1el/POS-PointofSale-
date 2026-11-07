package com.Moses.Moses.POS.System.payload.dto.branchDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchSalesDTO {
    private Long branchId;
    private String branchName;
    private BigDecimal totalSales;
}
