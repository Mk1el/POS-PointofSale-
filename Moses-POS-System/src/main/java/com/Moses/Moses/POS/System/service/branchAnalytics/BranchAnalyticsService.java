package com.Moses.Moses.POS.System.service.branchAnalytics;

import com.Moses.Moses.POS.System.payload.dto.dailysalesDto.*;

import java.time.LocalDate;
import java.util.List;

public interface BranchAnalyticsService {

    List<DailySalesDTO> getDailySales(
            Long branchId,
            LocalDate startDate,
            LocalDate endDate
    );

    List<DailySalesDTO> getDailySales(
            Long branchId,
            int days
    );

    List<TopProductDTO> getTopProducts(
            Long branchId,
            LocalDate date
    );

    List<TopCashierDTO> getTopCashiers(
            Long branchId,
            LocalDate date
    );

    List<CategorySalesDTO> getCategorySales(
            Long branchId,
            LocalDate date
    );

    TodayOverviewDTO getTodayOverview(Long branchId);

    List<PaymentBreakdownDTO> getPaymentBreakdown(
            Long branchId,
            LocalDate date
    );
}