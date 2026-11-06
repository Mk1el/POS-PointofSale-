package com.Moses.Moses.POS.System.payload.dto.dashboardDto;

public record StoreStatusDistributionDTO (
        long active,
        long blocked,
        long pending
){
}
