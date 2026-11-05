package com.Moses.Moses.POS.System.controller;

import com.Moses.Moses.POS.System.payload.dto.dailysalesDto.*;
import com.Moses.Moses.POS.System.service.branchAnalytics.BranchAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/branch-analytics")
@RequiredArgsConstructor
public class BranchAnalyticsController {

    private final BranchAnalyticsService analyticsService;

    @GetMapping("/daily-sales")
    public ResponseEntity<List<DailySalesDTO>> dailySales(
            @RequestParam Long branchId,
            @RequestParam(defaultValue = "7") int days) {

        return ResponseEntity.ok(
                analyticsService.getDailySales(branchId, days)
        );
    }

    @GetMapping("/top-products")
    public ResponseEntity<List<TopProductDTO>> topProducts(
            @RequestParam Long branchId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {

        return ResponseEntity.ok(
                analyticsService.getTopProducts(
                        branchId,
                        date == null ? LocalDate.now() : date
                )
        );
    }

    @GetMapping("/top-cashiers")
    public ResponseEntity<List<TopCashierDTO>> topCashiers(
            @RequestParam Long branchId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {

        return ResponseEntity.ok(
                analyticsService.getTopCashiers(
                        branchId,
                        date == null ? LocalDate.now() : date
                )
        );
    }

    @GetMapping("/category-sales")
    public ResponseEntity<List<CategorySalesDTO>> categorySales(
            @RequestParam Long branchId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {

        return ResponseEntity.ok(
                analyticsService.getCategorySales(branchId, date)
        );
    }

    @GetMapping("/today-overview")
    public ResponseEntity<TodayOverviewDTO> todayOverview(
            @RequestParam Long branchId) {

        return ResponseEntity.ok(
                analyticsService.getTodayOverview(branchId)
        );
    }

    @GetMapping("/payment-breakdown")
    public ResponseEntity<List<PaymentBreakdownDTO>> paymentBreakdown(
            @RequestParam Long branchId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {

        return ResponseEntity.ok(
                analyticsService.getPaymentBreakdown(branchId, date)
        );
    }
}