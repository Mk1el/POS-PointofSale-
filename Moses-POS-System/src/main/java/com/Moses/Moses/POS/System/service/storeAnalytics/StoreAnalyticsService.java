package com.Moses.Moses.POS.System.service.storeAnalytics;

import com.Moses.Moses.POS.System.payload.dto.alertDto.AlertDTO;
import com.Moses.Moses.POS.System.payload.dto.branchDto.BranchPerformanceDTO;
import com.Moses.Moses.POS.System.payload.dto.branchDto.BranchSalesDTO;
import com.Moses.Moses.POS.System.payload.dto.dailysalesDto.CategorySalesDTO;
import com.Moses.Moses.POS.System.payload.dto.dailysalesDto.DailySalesDTO;
import com.Moses.Moses.POS.System.payload.dto.dailysalesDto.PaymentBreakdownDTO;
import com.Moses.Moses.POS.System.payload.dto.salesTrendDto.MonthlySalesDTO;
import com.Moses.Moses.POS.System.payload.dto.salesTrendDto.SalesTrendDTO;
import com.Moses.Moses.POS.System.payload.dto.storeAnalyticsDto.OverviewKpiDTO;
import com.Moses.Moses.POS.System.payload.payment.PaymentMethodSalesDTO;

import java.util.List;

public interface StoreAnalyticsService {
    OverviewKpiDTO getOverview(Long storeAdminId);
    List<SalesTrendDTO> getSalesTrends(Long storeAdminId, String period);
    List<MonthlySalesDTO> getMonthlySales(Long storeAdminId);
    List<DailySalesDTO> getDailySales(Long storeAdminId);
    List<CategorySalesDTO> getCategorySales(Long storeAdminId);
    List<PaymentMethodSalesDTO> getPaymentMethodSales(Long storeAdminId);
    List<BranchSalesDTO> getSalesByBranch(Long storeAdminId);
    List<PaymentBreakdownDTO> getPaymentBreakdown(Long storeAdminId);
    List<BranchPerformanceDTO> getBranchPerformance(Long storeAdminId);
    List<AlertDTO> getAlerts(Long storeAdminId);
}
