package com.Moses.Moses.POS.System.controller;

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
import com.Moses.Moses.POS.System.service.storeAnalytics.StoreAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/store/analytics/{storeAdminId}")
@RequiredArgsConstructor
public class StoreAnalyticsController {

    private final StoreAnalyticsService analyticsService;

    // 1. Overall KPI summary
    @GetMapping("/overview")
    public ResponseEntity<OverviewKpiDTO> getOverview(@PathVariable Long storeAdminId) {
        return ResponseEntity.ok(analyticsService.getOverview(storeAdminId));
    }

    // 2. Sales trends with query parameter (e.g. daily, weekly)
    @GetMapping("/sales-trends")
    public ResponseEntity<List<SalesTrendDTO>> getSalesTrends(
            @PathVariable Long storeAdminId,
            @RequestParam(defaultValue = "daily") String period) {
        return ResponseEntity.ok(analyticsService.getSalesTrends(storeAdminId, period));
    }

    // 3. Monthly sales data points
    @GetMapping("/sales/monthly")
    public ResponseEntity<List<MonthlySalesDTO>> getMonthlySales(@PathVariable Long storeAdminId) {
        return ResponseEntity.ok(analyticsService.getMonthlySales(storeAdminId));
    }

    // 4. Daily sales data points
    @GetMapping("/sales/daily")
    public ResponseEntity<List<DailySalesDTO>> getDailySales(@PathVariable Long storeAdminId) {
        return ResponseEntity.ok(analyticsService.getDailySales(storeAdminId));
    }

    // 5. Sales distribution by category
    @GetMapping("/sales/category")
    public ResponseEntity<List<CategorySalesDTO>> getSalesByCategory(@PathVariable Long storeAdminId) {
        return ResponseEntity.ok(analyticsService.getCategorySales(storeAdminId));
    }

    // 6. Sales grouped by payment method (UPI, cash, card)
    @GetMapping("/sales/payment-method")
    public ResponseEntity<List<PaymentMethodSalesDTO>> getSalesByPaymentMethod(@PathVariable Long storeAdminId) {
        return ResponseEntity.ok(analyticsService.getPaymentMethodSales(storeAdminId));
    }

    // 7. Total sales per branch
    @GetMapping("/sales/branch")
    public ResponseEntity<List<BranchSalesDTO>> getSalesByBranch(@PathVariable Long storeAdminId) {
        return ResponseEntity.ok(analyticsService.getSalesByBranch(storeAdminId));
    }

    // 8. Detailed breakdown of payments
    @GetMapping("/payments")
    public ResponseEntity<List<PaymentBreakdownDTO>> getPaymentBreakdown(@PathVariable Long storeAdminId) {
        return ResponseEntity.ok(analyticsService.getPaymentBreakdown(storeAdminId));
    }

    // 9. Performance metrics per branch
    @GetMapping("/branch-performance")
    public ResponseEntity<List<BranchPerformanceDTO>> getBranchPerformance(@PathVariable Long storeAdminId) {
        return ResponseEntity.ok(analyticsService.getBranchPerformance(storeAdminId));
    }

    // 10. Alerts or issues (low stock, inactive cashiers, etc.)
    @GetMapping("/alerts")
    public ResponseEntity<List<AlertDTO>> getAlerts(@PathVariable Long storeAdminId) {
        return ResponseEntity.ok(analyticsService.getAlerts(storeAdminId));
    }
}
