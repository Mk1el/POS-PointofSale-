package com.Moses.Moses.POS.System.mapper;

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
import com.Moses.Moses.POS.System.repository.StoreAnalyticsRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class StoreAnalyticsMapper {

    // Helper to safely convert BigDecimal to Double
    private Double toDouble(BigDecimal value) {
        return value != null ? value.doubleValue() : 0.0;
    }

    // Helper to safely obtain non-null BigDecimal
    private BigDecimal toBigDecimal(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }

    public OverviewKpiDTO toOverviewKpiDTO(StoreAnalyticsRepository.KpiProjection projection) {
        if (projection == null) {
            return new OverviewKpiDTO(0L, BigDecimal.ZERO, 0L, BigDecimal.ZERO);
        }
        BigDecimal revenue = toBigDecimal(projection.getTotalRevenue());
        Long orders = projection.getTotalOrders() != null ? projection.getTotalOrders() : 0L;
        BigDecimal aov = orders > 0
                ? revenue.divide(BigDecimal.valueOf(orders), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        return OverviewKpiDTO.builder()
                .totalOrders(orders)
                .totalRevenue(revenue)
                .totalCustomers(projection.getTotalCustomers() != null ? projection.getTotalCustomers() : 0L)
                .averageOrderValue(aov)
                .build();
    }

    public SalesTrendDTO toSalesTrendDTO(StoreAnalyticsRepository.SalesTrendProjection projection) {
        return SalesTrendDTO.builder()
                .periodLabel(projection.getPeriodLabel())
                .revenue(toBigDecimal(projection.getRevenue()))
                .orderCount(projection.getOrderCount() != null ? projection.getOrderCount() : 0L)
                .build();
    }

    public MonthlySalesDTO toMonthlySalesDTO(StoreAnalyticsRepository.MonthlySalesProjection projection) {
        return MonthlySalesDTO.builder()
                .month(projection.getMonth())
                .revenue(toBigDecimal(projection.getRevenue()))
                .orders(projection.getOrders() != null ? projection.getOrders() : 0L)
                .build();
    }

    public DailySalesDTO toDailySalesDTO(StoreAnalyticsRepository.DailySalesProjection projection) {
        BigDecimal revenue = toBigDecimal(projection.getRevenue());
        Long orders = projection.getTotalOrders() != null ? projection.getTotalOrders() : 0L;

        return DailySalesDTO.builder()
                .date(projection.getSaleDate())
                .totalOrders(orders)
                .totalSales(toDouble(revenue)) // Safely converted to Double
                .revenue(revenue)             // Kept as BigDecimal
                .build();
    }

    public CategorySalesDTO toCategorySalesDTO(StoreAnalyticsRepository.CategorySalesProjection projection) {
        return CategorySalesDTO.builder()
                .categoryId(projection.getCategoryId())
                .categoryName(projection.getCategoryName())
                .quantitySold(projection.getQuantitySold() != null ? projection.getQuantitySold() : 0L)
                .totalSales(toBigDecimal(projection.getTotalSales()))
                .build();
    }

    public PaymentMethodSalesDTO toPaymentMethodSalesDTO(StoreAnalyticsRepository.PaymentMethodProjection projection) {
        return PaymentMethodSalesDTO.builder()
                .paymentMethod(projection.getPaymentMethod())
                .totalAmount(toBigDecimal(projection.getTotalAmount()))
                .transactionCount(projection.getCount() != null ? projection.getCount() : 0L)
                .build();
    }

    public BranchSalesDTO toBranchSalesDTO(StoreAnalyticsRepository.BranchSalesProjection projection) {
        BigDecimal sales = toBigDecimal(projection.getTotalSales());

        return BranchSalesDTO.builder()
                .branchId(projection.getBranchId())
                .branchName(projection.getBranchName())
                .totalSales(sales) // If your BranchSalesDTO has Double totalSales, replace with: toDouble(sales)
                .build();
    }

    public PaymentBreakdownDTO toPaymentBreakdownDTO(StoreAnalyticsRepository.PaymentBreakdownProjection projection) {
        return PaymentBreakdownDTO.builder()
                .paymentMethod(projection.getPaymentMethod())
                .totalTransactions(projection.getTotalTransactions() != null ? projection.getTotalTransactions() : 0L)
                .totalAmount(toBigDecimal(projection.getTotalAmount()))
                .successfulTransactions(projection.getSuccessfulTransactions() != null ? projection.getSuccessfulTransactions() : 0L)
                .failedTransactions(projection.getFailedTransactions() != null ? projection.getFailedTransactions() : 0L)
                .build();
    }

    public BranchPerformanceDTO toBranchPerformanceDTO(StoreAnalyticsRepository.BranchSalesProjection projection) {
        BigDecimal sales = toBigDecimal(projection.getTotalSales());
        Long orders = projection.getOrderCount() != null ? projection.getOrderCount() : 0L;
        BigDecimal aov = orders > 0
                ? sales.divide(BigDecimal.valueOf(orders), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        return BranchPerformanceDTO.builder()
                .branchId(projection.getBranchId())
                .branchName(projection.getBranchName())
                .totalOrders(orders)
                .totalRevenue(sales)            // If your DTO uses Double, change to: toDouble(sales)
                .averageOrderValue(aov)         // If your DTO uses Double, change to: toDouble(aov)
                .build();
    }

    public AlertDTO toAlertDTO(StoreAnalyticsRepository.AlertProjection projection) {
        return AlertDTO.builder()
                .id(projection.getId())
                .type(projection.getType())
                .severity(projection.getSeverity())
                .message(projection.getMessage())
                .branchId(projection.getBranchId())
                .timestamp(projection.getTimestamp())
                .build();
    }
}