package com.Moses.Moses.POS.System.payload.dto.dashboardDto;

public record DashboardSummaryDTO (
    long totalSales,
    long activeStores,
    long pendingStores,
    long blockedStores

    ){}
